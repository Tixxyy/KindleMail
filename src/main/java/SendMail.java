import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.Gmail;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;
import java.util.ArrayList;

class SendMail{

    public static MimeMessage createEmailWithAttachment(String to,
                                                        String from,
                                                        String subject,
                                                        String bodyText,
                                                        ArrayList<File> files)
            throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(bodyText, "text/plain");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        for (File file : files){
            mimeBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            
            mimeBodyPart.setDataHandler(new DataHandler(source));
            mimeBodyPart.setFileName(file.getName());

            multipart.addBodyPart(mimeBodyPart);
            }

        email.setContent(multipart);
        return email;
    }

    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    public static Message sendMessage(Gmail service,
                                      String userId,
                                      MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
        return message;
    }
}
