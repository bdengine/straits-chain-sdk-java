package com.shangchain.straitchain.exception;

import com.shangchain.straitchain.dto.StraitChainResponse;

/**
 * 2022/4/26
 * 异常
 *
 * @author shangchain Junisyoan
 */
public class StraitChainException extends RuntimeException{
    private Integer code;
    private StraitChainResponse.Error error;
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

    public StraitChainException(StraitChainResponse.Error error) {
        super(error.getMessage());
        this.error = error;
        this.code = error.getCode();
    }

    public StraitChainException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return code;
    }

    public StraitChainResponse.Error getError() {
        return error;
    }

    public void setError(StraitChainResponse.Error error) {
        this.error = error;
    }
}
