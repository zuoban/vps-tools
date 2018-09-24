package com.zuoban.toy.vpstools.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.zuoban.toy.vpstools.constants.UserConstants;
import com.zuoban.toy.vpstools.controller.vo.UserRegisterVO;
import com.zuoban.toy.vpstools.entity.User;
import com.zuoban.toy.vpstools.properties.AppProperties;
import com.zuoban.toy.vpstools.service.UserService;
import com.zuoban.toy.vpstools.supports.bean.RestResponse;
import com.zuoban.toy.vpstools.supports.bean.RestResponseEnum;
import com.zuoban.toy.vpstools.supports.holder.UserHolder;
import com.zuoban.toy.vpstools.supports.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */
@RestController
@RequestMapping("user")
@Validated
public class UserController {
    private final UserService userService;

    private final RedisTemplate<String, String> redisTemplate;

    private final AppProperties appProperties;

    @Autowired
    public UserController(UserService userService, RedisTemplate<String, String> redisTemplate, AppProperties appProperties) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.appProperties = appProperties;
    }

    @PostMapping("login")
    public RestResponse login(@NotEmpty String username, @NotEmpty String password) {

        String passwordInDB = PasswordUtil.getPassword(username, password);

        Optional<User> userOptional = userService.findByUsernameAndPassword(username, passwordInDB);

        User user = userOptional.orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        user.setPassword(null);

        String uuid = IdUtil.simpleUUID();

        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(UserConstants.USER_TOKEN_KEY_PREFIX + uuid);
        ops.set(JSONUtil.toJsonStr(user), 30, TimeUnit.MINUTES);

        return RestResponse.success(uuid).setMessage("登录成功");
    }

    @GetMapping("validate")
    public RestResponse validate(String type, String value) {
        if ("username".equals(type)) {
            boolean valid = !userService.existsByUsername(value);
            return RestResponse.success(valid).setMessage(valid ? "用户名可用" : "用户名已存在");
        } else if ("email".equals(type)) {
            boolean valid = !userService.existsByEmail(value);
            return RestResponse.success(valid).setMessage(valid ? "邮箱已存在" : "邮箱可用");
        }
        return RestResponse.fail().setMessage("不支持的类型");
    }

    @PostMapping("register")
    public RestResponse register(@Validated UserRegisterVO vo) {
        Assert.isTrue(appProperties.getOpenRegister(), "未开发注册");
        userService.register(vo);
        return RestResponseEnum.REGISTER_SUCCESS.getTarget();
    }

    @GetMapping("info")
    public RestResponse info() {
        User user = UserHolder.getUser();
        return RestResponse.success(user);
    }

    @PostMapping("logout")
    public RestResponse logout(@RequestHeader("X-Token") String token) {
        String key = UserConstants.USER_TOKEN_KEY_PREFIX + token;
        redisTemplate.delete(key);
        return RestResponseEnum.LOGOUT_SUCCESS.getTarget();
    }
}
