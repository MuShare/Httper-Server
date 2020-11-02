package org.mushare.httper.bean;

import org.mushare.httper.domain.Request;

public class RequestBean {

    private String rid;
    private String url;
    private String method;
    private long updateAt;
    private String headers;
    private String parameters;
    private String bodyType;
    private String body;
    private boolean deleted;
    private int revision;
    private String uid;
    private String pid;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public RequestBean(Request request) {
        this.rid = request.getRid();
        this.deleted = request.getDeleted();
        this.revision = request.getRevision();
        this.uid = request.getUser().getUid();
        this.pid = (request.getProject() == null) ? null : request.getProject().getPid();
        if (!request.getDeleted()) {
            this.url = request.getUrl();
            this.method = request.getMethod();
            this.updateAt = request.getUpdateAt();
            this.headers = request.getHeaders();
            this.parameters = request.getParameters();
            this.bodyType = request.getBodyType();
            this.body = request.getBody();
        }
    }
}
