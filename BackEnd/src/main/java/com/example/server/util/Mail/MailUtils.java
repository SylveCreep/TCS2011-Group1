package com.example.server.util.Mail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.stereotype.Service;

@Service
public class MailUtils {
    ResourceBundle rb = ResourceBundle.getBundle("email");
    String host_email = rb.getString("SEND_FROM");
    String username = rb.getString("USERNAME");
    String pwd = rb.getString("PASSWORD");
    String mail_host = rb.getString("MAIL_HOST");
    String mail_port = rb.getString("MAIL_PORT");

    private Session createSession() throws IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", mail_host);
        props.put("mail.smtp.ssl.enable", false);
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.timeout", 60000);
        props.put("mail.smtp.connectiontimeout", 60000);
        props.put("mail.smtp.port", mail_port);

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, pwd);
            }
        });
    }

    private MimeMessage prepareEmailMessage(MimeMessage messageMime, String sendFrom, String sendTo, List<String> ccList,
            String subject, String message, String type) throws MessagingException, IOException {
        messageMime.setContent(message, type);
        messageMime.setFrom(new InternetAddress(sendFrom));
        messageMime.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
        if (!ccList.isEmpty()) {
            ccList.stream().forEach(cc -> {
                try {
                    messageMime.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
                } catch (AddressException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        messageMime.setSubject(subject);
        return messageMime;
    }

    private MimeMessage prepareMultipartEmailMessage(MimeMessage messageMime,String sendFrom, String sendTo, List<String> ccList, String subject,
            String message, String fileUrl) throws Exception {
        messageMime.setFrom(new InternetAddress(sendFrom));
        messageMime.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
        if (!ccList.isEmpty()) {
            ccList.stream().forEach(cc -> {
                try {
                    messageMime.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
                } catch (AddressException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        messageMime.setSubject(subject);
        // create multipart message
        Multipart multipart = new MimeMultipart();

        // create message part and add to body part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(subject);
        multipart.addBodyPart(messageBodyPart);

        // create attachment part and add to body part
        messageBodyPart = new MimeBodyPart();
        ((MimeBodyPart) messageBodyPart).attachFile(new File(this.getClass().getResource(fileUrl).toURI()));
        messageBodyPart.setFileName(fileUrl.substring(1));
        multipart.addBodyPart(messageBodyPart);
        messageMime.setContent(multipart);
        return messageMime;
    }

    public String sendAsHtml(String sendFrom, String sendTo, List<String> ccList, String subject, String html) throws MessagingException, IOException {
        try {
            Session session = createSession();

            // create message using session
            MimeMessage messageMime = new MimeMessage(session);
            messageMime = prepareEmailMessage(messageMime, sendFrom, sendTo, ccList, subject, html, "text/html; charset=utf-8");

            // sending message
            Transport.send(messageMime);
            return null;
        } catch (Exception e) {
            //e.printStackTrace();
            return "Sending attempt failed";
        }
    }

    public String sendAsMessage(String sendFrom, String sendTo, List<String> ccList, String subject, String message) throws MessagingException, IOException {
        try {
            Session session = createSession();
            // create message using session
            MimeMessage messageMime = new MimeMessage(session);
            messageMime = prepareEmailMessage(messageMime, sendFrom, sendTo, ccList, subject, message, "text/plain");

            // sending message
            Transport.send(messageMime);
            return null;
        } catch (Exception e) {
            return "Sending attempt failed";
        }
    }

    public String sendWithAttachment(String sendFrom, String sendTo, List<String> ccList, String subject, String message, String fileUrl) throws Exception {
        try {
            Session session = createSession();

            // create message using session
            MimeMessage messageMime = new MimeMessage(session);
            messageMime = prepareMultipartEmailMessage(messageMime, sendFrom, sendTo, ccList, subject, message, fileUrl);

            // sending message
            Transport.send(messageMime);
            return null;
        } catch (Exception e) {
            //e.printStackTrace();
            return "Sending attempt failed";
        }
    }
}
