package org.mushare.httper.bean;

import org.mushare.httper.domain.Project;

public class ProjectBean {

    private String pid;
    private String pname;
    private String privilege;
    private String introduction;
    private long updateAt;
    private boolean deleted;
    private int revision;
    private String uid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ProjectBean(Project project) {
        this.pid = project.getPid();
        this.deleted = project.getDeleted();
        this.revision = project.getRevision();
        this.uid = project.getUser().getUid();
        if (!project.getDeleted()) {
            this.pname = project.getPname();
            this.privilege = project.getPrivilege();
            this.introduction = project.getIntroduction();
            this.updateAt = project.getUpdateAt();
        }
    }
}
