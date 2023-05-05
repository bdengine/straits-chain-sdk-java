package com.shangchain.straitchain.exception;

public class EnsFormatException extends RuntimeException {
    public EnsFormatException() {
        super("Invalid Ens Name");
    }
}
