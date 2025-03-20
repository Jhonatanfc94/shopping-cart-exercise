package auxiliary.tools;

import auxiliary.Data;
import org.testng.Assert;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class SendEmail {
    final static String ADDRESSEE = Data.emailTesting.userRegression;
    final static String SENDER = Data.emailTesting.user;
    final static String PASSWORD = Data.emailTesting.password;

    public SendEmail() throws NoSuchAlgorithmException {
    }

    public static void sendEmailWithCapture(String subject, String body) {
        screenshot();
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getDefaultInstance(properties);
        try {
            InternetAddress fromAddress = new InternetAddress(SENDER);
            InternetAddress toAddress = new InternetAddress(ADDRESSEE);
            Message message = new MimeMessage(session);
            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.saveChanges();

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setContent(body, "text/html; charset=utf-8");
            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = System.getProperty("user.dir") + "\\screenshot\\screenshot.png";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);
            Transport.send(message, SENDER, PASSWORD);
        } catch (MessagingException e) {
            Assert.fail(e.getMessage());
        }
    }

    public static void sendEmail(String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getDefaultInstance(properties);
        try {
            InternetAddress fromAddress = new InternetAddress(SENDER);
            InternetAddress toAddress = new InternetAddress(ADDRESSEE);
            Message message = new MimeMessage(session);
            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.saveChanges();

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setContent(body, "text/html; charset=utf-8");
            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);
            Transport.send(message, SENDER, PASSWORD);
        } catch (MessagingException e) {
            Assert.fail(e.getMessage());
        }
    }

    public static void screenshot() {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            System.out.println(System.getProperty("user.dir") + "\\screenshot\\screenshot.png");
            ImageIO.write(image, "png", new File(System.getProperty("user.dir") + "\\screenshot\\screenshot.png"));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}