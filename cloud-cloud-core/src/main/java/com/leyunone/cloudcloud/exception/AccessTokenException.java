package com.leyunone.cloudcloud.exception;

/**
 * :)
 *  令牌异常
 * @Author LeYunone
 * @Date 2024/6/18 10:54
 */
public class AccessTokenException extends RuntimeException {

    public AccessTokenException() {
        super();
    }

    public AccessTokenException(String message) {
        super(message);
    }

    public AccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessTokenException(Throwable cause) {
        super(cause);
    }

}
