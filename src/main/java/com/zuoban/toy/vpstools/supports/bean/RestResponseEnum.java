package com.zuoban.toy.vpstools.supports.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */
@AllArgsConstructor
@Getter
public enum RestResponseEnum {
    /**
     * 添加成功
     */
    ADD_SUCCESS(RestResponse.success().setMessage("添加成功!")),
    UPDATE_SUCCESS(RestResponse.success().setMessage("修改成功!")),
    DELETE_SUCCESS(RestResponse.success().setMessage("删除成功!")),
    LOGIN_SUCCESS(RestResponse.success().setMessage("登录成功!")),
    LOGOUT_SUCCESS(RestResponse.success().setMessage("注销成功!")),
    REGISTER_SUCCESS(RestResponse.success().setMessage("注册成功!")),
    ;

    private RestResponse target;
}
