package com.zuoban.toy.vpstools.service;

import cn.hutool.core.bean.BeanUtil;
import com.zuoban.toy.vpstools.entity.UserFile;
import com.zuoban.toy.vpstools.repository.UserFileRepository;
import com.zuoban.toy.vpstools.service.base.BaseService;
import com.zuoban.toy.vpstools.supports.bean.BaseQuery;
import com.zuoban.toy.vpstools.supports.holder.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户文件 service
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@Service
public class UserFileService extends BaseService<UserFile, UserFileRepository> {
    private final UserFileRepository userFileRepository;

    @Autowired
    public UserFileService(UserFileRepository userFileRepository) {
        this.userFileRepository = userFileRepository;
    }

    public List<UserFile> findByUserId(Long userId) {
        UserFile uf = new UserFile().setUserId(userId);
        return userFileRepository.findAll(Example.of(uf));
    }

    public List<UserFile> findByUserIdAndFilename(Long userId, String filename) {
        UserFile uf = new UserFile().setUserId(userId).setFilename(filename);
        return userFileRepository.findAll(Example.of(uf));
    }

    public Page<UserFile> list(BaseQuery query) {
        UserFile userFile = new UserFile();
        userFile.setFilename(query.getKeywords());
        userFile.setUserId(UserHolder.getUserId());
        BeanUtil.fillBeanWithMap(query.ext2Map(), userFile, true);



        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("filename", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<UserFile> example = Example.of(userFile, matcher);

        return repository.findAll(example, query.toPageable());
    }
}

