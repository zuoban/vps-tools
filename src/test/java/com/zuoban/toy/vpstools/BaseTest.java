package com.zuoban.toy.vpstools;

import com.zuoban.toy.vpstools.entity.User;
import com.zuoban.toy.vpstools.supports.holder.UserHolder;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {
    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("zuoban");
        UserHolder.setCurrentUser(user);
    }
}
