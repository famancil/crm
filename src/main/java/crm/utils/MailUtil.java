package crm.utils;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

@Component
public class MailUtil {

    private static JavaMailSenderImpl mailSender;
    private static Properties properties;
    private static MimeMessage mimeMessage;
    private static MimeMessageHelper mailMsg;

    MailUtil(){
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("no-responder@sansanos.cl");
        mailSender.setPassword("hb55km.r9.");

        properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        mimeMessage = mailSender.createMimeMessage();
        mailMsg = new MimeMessageHelper(mimeMessage);
    }

    public void sendMail(String from, List<String> destinations, String subject, String msg) throws MessagingException, UnsupportedEncodingException {
        mailMsg.setFrom(new InternetAddress(from, "Sansanos [Vinculacion]"));

        for(String to: destinations) mailMsg.addBcc(to);
        mailMsg.setSubject(subject);
        mailMsg.setText(msg,true);
        mailSender.send(mimeMessage);
    }

    public static JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

    public static void setMailSender(JavaMailSenderImpl mailSender) {
        MailUtil.mailSender = mailSender;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        MailUtil.properties = properties;
    }

    public static MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    public static void setMimeMessage(MimeMessage mimeMessage) {
        MailUtil.mimeMessage = mimeMessage;
    }

    public static MimeMessageHelper getMailMsg() {
        return mailMsg;
    }

    public static void setMailMsg(MimeMessageHelper mailMsg) {
        MailUtil.mailMsg = mailMsg;
    }
}
