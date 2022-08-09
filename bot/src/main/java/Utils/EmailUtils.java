package Utils;

import Enums.LogTypeEnum;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.function.Consumer;

public class EmailUtils {

    public static void writeEmail(String recepient, String subject, String content, Consumer<Boolean> callback){

        Utils.LogSystem.log(LogTypeEnum.INFO, "trying to send email to " + recepient, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "mail.ncodes.eu");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.trust", "mail.ncodes.eu");

        try {

            Message message = new MimeMessage(SecretClass.getEmailSercret(prop));
            message.setFrom(new InternetAddress("ncodes@ncodes.eu"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(recepient));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            callback.accept(true);

        } catch (MessagingException e) {
            Utils.LogSystem.log(LogTypeEnum.INFO, "email can't be send. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            callback.accept(false);
        }

    }

}
