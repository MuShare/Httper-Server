package org.fczm.httper.controller.common;

public enum ErrorCode {
    ErrorToken(901, "Token is wrong."),

    ErrorEmailExist(1011, "This email has been registered."),
    ErrorEmailNotExist(1021, "This email is not exsit."),
    ErrorPasswordWrong(1022, "Password is wrong."),

    ErrorAddRequest(2011, "Add request failed because of an internel error."),
    ErrorDeleteRequestNotFound(2021, "Request not found. This may caused by commiting a wrong request"),
    ErrorDeleteRequestNoPrivilege(2022, "This user has not privilege to delete this request.");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
