package com.zuoban.toy.vpstools.supports.util;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */
public class PasswordUtil {
    private PasswordUtil() {
    }

    private static final String PASSWORD_SALT = "@#!zouban*(_";

    public static String getPassword(String username, String password) {
        byte[] bytes = DigestUtil.md5(username + password + PASSWORD_SALT);
        return DigestUtil.md5Hex(bytes);
    }

}
