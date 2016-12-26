package org.fczm.httper.controller.util;

public enum ErrorCode {
    ErrorToken(901, "Token is wrong."),

    ErrorEmailExist(1011, "This email has been registered."),
    ErrorEmailNotExist(1021, "This email is not exsit."),
    ErrorPasswordWrong(1022, "Password is wrong.");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
