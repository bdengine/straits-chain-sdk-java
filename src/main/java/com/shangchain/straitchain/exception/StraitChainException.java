package com.shangchain.straitchain.exception;

/**
 * 2022/4/26
 * 异常
 *
 * @author shangchain Junisyoan
 */
public class StraitChainException extends RuntimeException{
    private Integer code;
    public StraitChainException(String message) {
        super(message);
    }

    public StraitChainException(Throwable cause) {
        super(cause);
    }

    public StraitChainException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public StraitChainException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return code;
    }
}
