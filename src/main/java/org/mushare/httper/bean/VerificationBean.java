package org.mushare.httper.bean;

import org.directwebremoting.annotations.DataTransferObject;
import org.mushare.httper.domain.Verification;

@DataTransferObject
public class VerificationBean {

    private String vid;
    private long createAt;
    private int type;
    private boolean active;
    private UserBean user;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public VerificationBean(Verification verification, String baseUrl) {
        this.vid = verification.getVid();
        this.createAt = verification.getCreateAt();
        this.type = verification.getType();
        this.active = verification.getActive();
        this.user = new UserBean(verification.getUser(), baseUrl, true);
    }
}
