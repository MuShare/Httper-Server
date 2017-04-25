package org.fczm.httper.controller.common;

public enum ErrorCode {
    ErrorToken(901, "Token is wrong."),

    ErrorEmailExist(1011, "This email has been registered."),
    ErrorEmailNotExist(1021, "This email is not exsit."),
    ErrorPasswordWrong(1022, "Password is wrong."),
    ErrorSendResetPasswordMail(1031, "Send reset password email failed."),
    ErrorFacebookAccessTokenInvalid(1041, "Facebook access token is invalid"),

    ErrorDeleteRequest(2011, "Cannot delete this request. This may caused by commiting a wrong request, or this user has not privilege to delete this request."),
    ErrorDeleteProject(3011, "Cannot delete this project.");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
