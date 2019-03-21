import java.io.IOException;
import java.security.GeneralSecurityException;
import java.awt.EventQueue;
import javax.mail.MessagingException;

public class Main{
    public static void main (String...args) throws IOException, GeneralSecurityException, MessagingException{
        EventQueue.invokeLater(new UserInterface());
    }
}
