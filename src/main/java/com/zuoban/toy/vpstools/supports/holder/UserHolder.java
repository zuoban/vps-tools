package com.zuoban.toy.vpstools.supports.holder;

import com.zuoban.toy.vpstools.entity.User;

/**
 * 用户 holder
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
public class UserHolder {
    private static final ThreadLocal<User> CURRENT_USER = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        CURRENT_USER.set(user);
    }

    /**
     * 如果没有登录会抛出异常
     *
     * @return 当前登录用户
     */
    public static User getUser() {
        return CURRENT_USER.get();
    }

    public static Long getUserId() {
        User user = getUser();
        return user == null ? null : user.getId();
    }

    public static void reset() {
        CURRENT_USER.remove();
    }

}
