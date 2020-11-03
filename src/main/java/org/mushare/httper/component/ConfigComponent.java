package org.mushare.httper.component;

import org.mushare.common.util.JsonTool;
import org.mushare.httper.component.config.Aliyun;
import org.mushare.httper.component.config.Facebook;
import org.mushare.httper.component.config.Global;
import org.mushare.httper.component.config.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

@Component
public class ConfigComponent {

    public static final String ConfigPath = "/WEB-INF/config.json";

    public String DefaultAvatar = "/static/images/avatar.png";
    public String AvatarPath = "/avatar";

    public String rootPath;
    public JsonTool configTool = null;
    public Mail mail;
    public Global global;
    public Facebook facebook;
    public Aliyun aliyun;

    public ConfigComponent() {
        rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        load();
    }

    public void load() {
        String pathname = rootPath + ConfigPath;
        configTool = new JsonTool(pathname);
        global = new Global(configTool.getJSONObject("global"));
        mail = new Mail(configTool.getJSONObject("mail"));
        facebook = new Facebook(configTool.getJSONObject("facebook"));
        aliyun = new Aliyun(configTool.getJSONObject("aliyun"));
    }

    public String getCachePath() {
        return rootPath + "/cache";
    };

}
