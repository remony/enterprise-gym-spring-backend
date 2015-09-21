package six.team.backend.utils;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
// Class created following tutorial from http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
public class Email {
    // Email reused from previous project
    private final String email = "agileapi@gmail.com";

    /*
            Create all of the properties required to connect to gmail
     */
    private Properties getProperties () {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        return props;
    }
    /*
            Create and return a session
     */
    Session getSession () {
        Session session = Session.getInstance(getProperties(), new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, "DundeeUni");
            }
        });
        return session;
    }
    /*
            Send the email returning true if success and false if failed
     */
    public boolean sendEmail(String recipient, String subject, String content) {

        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            return true;
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return false;
    }


}
