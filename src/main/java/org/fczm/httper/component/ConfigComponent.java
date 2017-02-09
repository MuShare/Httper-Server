package org.fczm.httper.component;

import org.springframework.stereotype.Component;

@Component
public class ConfigComponent {

    private String httpProtocol;
    private String domain;

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
}
