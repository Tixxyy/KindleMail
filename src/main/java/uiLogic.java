import com.google.api.services.gmail.Gmail;
import java.io.File;
import java.util.ArrayList;
import javax.mail.internet.MimeMessage;

public class uiLogic{
    public static boolean sendMessage(String to, String from,ArrayList<File> files){
        try{
            Gmail service = GetService.getService();
            MimeMessage mimeMessage = SendMail.createEmailWithAttachment(to, from, "", "", files);
            SendMail.sendMessage(service, "me", mimeMessage);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}