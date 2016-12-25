package org.fczm.httper.bean;

import org.fczm.httper.domain.User;

public class UserBean {

    private String uid;
    private String name;
    private String avatar;
    private String type;
    private String identifier;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public UserBean(User user) {
        this.uid = user.getUid();
        this.name = user.getName();
        this.avatar = user.getAvatar();
        this.type = user.getType();
        this.identifier = user.getIdentifier();
    }
}
