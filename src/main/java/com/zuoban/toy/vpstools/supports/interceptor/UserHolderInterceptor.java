package com.zuoban.toy.vpstools.supports.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.zuoban.toy.vpstools.constants.UserConstants;
import com.zuoban.toy.vpstools.entity.User;
import com.zuoban.toy.vpstools.supports.bean.RestResponse;
import com.zuoban.toy.vpstools.supports.holder.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户拦截器
 * 将用户放在 UserHolder 中
 *
 * @author wangjinqiang
 * @date 2018-07-22
 */
@Component
public class UserHolderInterceptor extends HandlerInterceptorAdapter {
    private final RedisTemplate<String, String> redisTemplate;

    private static final List<String> WHITE_LIST_URI = CollUtil.newArrayList("/user/register", "/user/login", "/user/validate");

    @Autowired
    public UserHolderInterceptor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        System.out.println("uri = " + uri);
        uri = uri.replace("/api", "");
        System.out.println("uri = " + uri);
        if (WHITE_LIST_URI.contains(uri)) {
            // 在白名单中， 直接放行。
            return true;
        }
        String token = ServletUtil.getHeader(request, "X-Token", "utf-8");

        if (StrUtil.isNotBlank(token)) {
            BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(UserConstants.USER_TOKEN_KEY_PREFIX + token);
            String userJsonStr = ops.get();
            if (StrUtil.isNotBlank(userJsonStr)) {
                User user = JSONUtil.toBean(userJsonStr, User.class);
                UserHolder.setCurrentUser(user);

                // 30分钟后登录失效
                ops.expire(30, TimeUnit.MINUTES);
                return true;
            }
        }

        String text = JSONUtil.toJsonStr(RestResponse.unauthorized().setMessage("请登录!"));
        ServletUtil.write(response, text, ContentType.JSON.toString() + ";charset=utf-8");
        return false;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserHolder.reset();
    }
}
