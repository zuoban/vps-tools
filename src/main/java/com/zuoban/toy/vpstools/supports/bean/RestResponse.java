package com.zuoban.toy.vpstools.supports.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 响应实体
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@Data
@Accessors(chain = true)
public class RestResponse<T> {

    private static final int FAIL = 500;

    private static final int SUCCESS = 200;

    private static final int UNAUTHORIZED = 401;

    private static final int FORBIDDEN = 403;

    private static final int BAD_REQUEST = 400;

    /**
     * 数据
     */
    private T data;

    /**
     * 响应码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<T>().setCode(SUCCESS).setData(data);
    }

    public static <T> RestResponse<T> success() {
        return new RestResponse<T>().setCode(SUCCESS);
    }

    public static <T> RestResponse<T> fail(T data) {
        return new RestResponse<T>().setCode(FAIL).setData(data);
    }

    public static <T> RestResponse<T> fail() {
        return new RestResponse<T>().setCode(FAIL);
    }

    public static <T> RestResponse<T> unauthorized() {
        return new RestResponse<T>().setCode(UNAUTHORIZED);
    }

    public static <T> RestResponse<T> badRequest() {
        return new RestResponse<T>().setCode(BAD_REQUEST);
    }

    public static <T> RestResponse<T> forbidden() {
        return new RestResponse<T>().setCode(FORBIDDEN);
    }
}
