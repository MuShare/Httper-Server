package org.fczm.httper.component.config;

import net.sf.json.JSONObject;

public class Facebook {

    public String appId;

    public Facebook(JSONObject object) {
        this.appId = object.getString("appId");
    }

}
