package org.mushare.httper.component.config;

import net.sf.json.JSONObject;

public class Global {

    // Supported languages
    public String [] languages;

    // Http protocal of Httper Web service.
    public String httpProtocol;

    // Domain nane of Httper Web service.
    public String domain;

    // Period of validity for verfication code, unit is second.
    public int validity;

    public Global(JSONObject object) {
        this.languages = object.getString("languages").split("/");
        this.httpProtocol = object.getString("httpProtocol");
        this.domain = object.getString("domain");
        this.validity = object.getInt("validity");
    }

}
