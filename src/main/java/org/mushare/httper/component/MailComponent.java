package org.mushare.httper.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Date;
import java.util.Properties;

@Component
public class MailComponent {

    @Autowired
    private ConfigComponent config;

    /**
     * Send mail by
     * @param receiver
     * @param subject
     * @param content
     * @return
     */
    public boolean send(String receiver, String subject, String content) {
        // Create property object for session
        Properties props = new Properties();
        props.put("mail.smtp.host", config.mail.smtp);
        props.put("mail.smtp.auth", "true");
        // Use SSL to send mail
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable", "true");
        // Create session object
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            // Auth by user name and password
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.mail.username, config.mail.password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            // Set mail sender and receiver.
            message.setFrom(new InternetAddress(config.mail.sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            // Set mail subject and content(using html)
            message.setSubject(transferToUTF8(subject));
            message.setContent(content, "text/html;charset=utf-8");
            // Set mail send date
            message.setSentDate(new Date());
            // Send mail
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Tansfer string to UTF-8
    public String transferToUTF8(String strText) {
        try {
            strText = MimeUtility.encodeText(new String(strText.getBytes(), "UTF-8"), "UTF-8", "B");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return strText;
    }

}
