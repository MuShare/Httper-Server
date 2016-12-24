package org.fczm.httper.service.util;

public enum UserType {

    Email("email");

    public String name;

    private UserType(String name) {
        this.name = name;
    }

}