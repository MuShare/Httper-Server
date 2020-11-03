package org.mushare.httper.component.config;

import net.sf.json.JSONObject;

public class Aliyun {
    public String baseUrl;
    public String endpoint;
    public String bucket;
    public String accessKeyId;
    public String accessKeySecret;

    public Aliyun(JSONObject object) {
        this.baseUrl = object.getString("baseUrl");
        this.endpoint = object.getString("endpoint");
        this.bucket = object.getString("bucket");
        this.accessKeyId = object.getString("accessKeyId");
        this.accessKeySecret = object.getString("accessKeySecret");
    }
}
