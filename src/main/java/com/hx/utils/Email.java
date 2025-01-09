package com.hx.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author dhx
 * @date 2025/1/8 18:25
 */
public class Email {
    private static String username;
    private static String password;
    public static void init(){
        username = ConfigUtil.getValue("email.username");
        password = ConfigUtil.getValue("email.password");
    }
    public static void sendMail(String to, String vcode) throws Exception {
        if(username==null)
            init();
        System.out.println(username);
        System.out.println(password);
        String host = "smtp.qq.com";

        Properties props = System.getProperties();

        props.setProperty("mail.smtp.host", host);

        props.put("mail.smtp.auth", "true");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();

        sf.setTrustAllHosts(true);

        props.put("mail.smtp.ssl.enable", "true");

        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getDefaultInstance(props, new Authenticator() {

            @Override

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, password);

            }

        });
        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("来自GitStar的验证码");

            message.setContent("<h1>来自GitStar的验证码邮件,请接收你的验证码：</h1><h3>你的验证码是：" + vcode + "，请妥善保管好你的验证码！</h3>", "text/html;charset=UTF-8");

            Transport.send(message);

        } catch (MessagingException mex) {

            mex.printStackTrace();

        }

    }
}
