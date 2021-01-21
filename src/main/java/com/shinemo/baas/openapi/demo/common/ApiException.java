package com.shinemo.baas.openapi.demo.common;

/**
 * 统一异常对象
 *
 * @author dongzj
 * @date 2020/10/9 10:54
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 4666861106427943772L;

    private int code = 500;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause, false, false);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
