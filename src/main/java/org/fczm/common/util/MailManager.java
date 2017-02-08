package org.fczm.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailManager {

    // Senders's email address
    private String sender;
    // SMTP server address
    private String smtp;
    // User name for SMTP server
    private String username ;
    // Password for SMTP server
    private String password ;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Send mail by
     * @param receiver
     * @param subject
     * @param content
     * @param attachments
     * @return
     */
    public boolean send(String receiver, String subject, String content, List<String> attachments) {
        // Create property object for session
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.auth", "true");
        // Use SSL to send mail
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable", "true");
        // Create session object
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            // Auth by user name and password
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            // Set mail sender and receiver.
            message.setFrom(new InternetAddress(sender));
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
