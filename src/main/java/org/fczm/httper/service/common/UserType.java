package org.fczm.httper.service.common;

public enum UserType {

    Email("email");

    public String name;

    private UserType(String name) {
        this.name = name;
    }

}