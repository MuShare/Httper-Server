package org.fczm.httper.component;

import org.springframework.stereotype.Component;

@Component
public class ConfigComponent {

    // Http protocal of Httper Web service.
    private String httpProtocol;

    // Domain nane of Httper Web service.
    private String domain;

    // Period of validity for verfication code, unit is second.
    private int validity;

    public String getHttpProtocol() {
        return httpProtocol;
    }

    public void setHttpProtocol(String httpProtocol) {
        this.httpProtocol = httpProtocol;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }
}
