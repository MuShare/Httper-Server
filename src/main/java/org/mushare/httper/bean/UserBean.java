package org.mushare.httper.bean;

import org.directwebremoting.annotations.DataTransferObject;
import org.mushare.httper.domain.User;

@DataTransferObject
public class UserBean {

    private String uid;
    private String name;
    private String avatar;
    private String type;
    private String identifier;
    private String credential;

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

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public UserBean(User user, boolean safe) {
        this.uid = user.getUid();
        this.name = user.getName();
        this.avatar = user.getAvatar();
        if (!safe) {
            this.type = user.getType();
            this.identifier = user.getIdentifier();
            this.credential = user.getCredential();
        }
    }

    public void safe() {
        this.type = null;
        this.identifier = null;
        this.credential = null;
    }
}
