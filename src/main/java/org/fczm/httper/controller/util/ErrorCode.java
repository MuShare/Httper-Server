package org.fczm.httper.controller.util;

public enum ErrorCode {
    ErrorMasterKey(901, "Master key is wrong."),

    ErrorEmailExsit(1011, "This email has been registered.");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
