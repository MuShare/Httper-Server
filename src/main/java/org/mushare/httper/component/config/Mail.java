package org.mushare.httper.component.config;

import net.sf.json.JSONObject;

public class Mail {

    // Senders's email address
    public String sender;

    // SMTP server address
    public String smtp;

    // User name for SMTP server
    public String username;

    // Password for SMTP server
    public String password;

    public Mail(JSONObject object) {
        this.sender = object.getString("sender");
        this.smtp = object.getString("smtp");
        this.username = object.getString("username");
        this.password = object.getString("password");
    }

}
