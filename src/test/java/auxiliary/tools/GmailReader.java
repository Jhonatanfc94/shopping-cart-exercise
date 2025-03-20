package auxiliary.tools;

import auxiliary.Data;
import org.testng.Assert;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;
import java.util.Properties;

public class GmailReader {

    static Properties properties;
    static Session session;
    static Store store;

    final static String USER = Data.emailTesting.user;
    final static String PASSWORD = Data.emailTesting.password;

    final static String HOST = "smtp.gmail.com";
    final static String PROTOCOL = "imaps";
    final static String INBOX = "INBOX";

    public final static int DE = 0;
    public final static int SUBJECT = 1;
    public final static int EMAILBODY = 2;

    private static void establishConnection(){
        properties = System.getProperties();
        session = Session.getInstance(properties);
        try{
            store = session.getStore(PROTOCOL);
            System.out.println(HOST);
            store.connect(HOST, USER, PASSWORD);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int getTotalEmailsInInbox(){
        establishConnection();
        int totalEmailInbox = -1;
        try{
            totalEmailInbox = store.getFolder(INBOX).getMessageCount();
            store.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return totalEmailInbox;
    }

    public static String[] getEmailsWithSubject(String subject){
        System.out.println("Search for emails with a subject: '" + subject + "'.");
        String[] encryptedMessage = null;
        try {
            establishConnection();
            Folder inbox = store.getFolder(INBOX);
            inbox.open(Folder.READ_ONLY);
            Message[] emailsFound = inbox.search(searchCriteria(subject));

            System.out.println(emailsFound.length + " emails with that subject were found, checking the last one.");

            int lastEmailId = emailsFound.length - 1;
            Message lastEmail = emailsFound[lastEmailId];
            encryptedMessage = parseMessage(lastEmail);
            inbox.close();
            store.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return encryptedMessage;
    }

    public static String[][] getMultipleEmailsWithSubject(String subject){
        System.out.println("Search for emails with a subject: '" + subject + "'.");
        String[][] encryptedMessage = null;
        establishConnection();
        try{
            Folder inbox = store.getFolder(INBOX);
            inbox.open(Folder.READ_ONLY);
            Message[] emailsFound = inbox.search(searchCriteria(subject));
            encryptedMessage = new String[emailsFound.length][3];

            System.out.println(emailsFound.length + " emails with that subject were found, checking the last one.");

            for (int i = 0; i < encryptedMessage.length; i++) {
                Message email_i = emailsFound[i];
                encryptedMessage[i] = parseMessage(email_i);
            }
            inbox.close();
            store.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return encryptedMessage;
    }

    public static int getTotalOfPostsWithTheSameSubject(String subject){
        int totalEmailInbox = -1;
        establishConnection();
        try{
            Folder inbox = store.getFolder(INBOX);
            inbox.open(Folder.READ_ONLY);
            totalEmailInbox = inbox.search(searchCriteria(subject)).length;
            store.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return totalEmailInbox;
    }

    public static boolean checkEmail(String subject, String keyword1, String keyword2){
        String[] email = getEmailsWithSubject(subject);
        email = htmlToString(email);
        String content = email[EMAILBODY];
        return content.contains(keyword1) && content.contains(keyword2);
    }

    public static boolean checkEmails(String subject, String keyword1, String keyword2){
        String[][] emails = getMultipleEmailsWithSubject(subject);
        for(String[] email : emails) {
            email = htmlToString(email);
            String content = email[EMAILBODY];
            if (content.contains(keyword1) && content.contains(keyword2)) {
                return true;
            }
        }
        return false;
    }

    public static void deleteAllMailsFromInbox(){
        establishConnection();
        try{
            Folder inbox = store.getFolder(INBOX);
            inbox.open(Folder.READ_WRITE);
            Message[] messages = inbox.getMessages();
            for (Message message : messages)
                message.setFlag(Flags.Flag.DELETED, true);
            inbox.close(true);
            store.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean waitForMailWithSubject(String subject, int attempts){
        boolean theMailArrived = false;
        try {
            for (int i = 1; i <= attempts; i++) {
                System.out.println("Waiting for mail... try: " + i);
                if (getTotalOfPostsWithTheSameSubject(subject) != 0) {
                    theMailArrived = true;
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return theMailArrived;
    }

    private static SearchTerm searchCriteria(String subject) {
        return new SearchTerm() {
            public boolean match(Message message) {
                try {
                    return message.getSubject().contains(subject);
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        };
    }

    public static String[] htmlToString(String[] email) {
        String[] returnArray = new String[3];
        returnArray[DE] = replaceSpecialCharacters(email[DE]);
        returnArray[SUBJECT] = replaceSpecialCharacters(email[SUBJECT]);
        returnArray[EMAILBODY] = email[EMAILBODY].replaceAll("</td><td>", " ");
        returnArray[EMAILBODY] = returnArray[EMAILBODY].replaceAll("</tr><tr>", " ");
        returnArray[EMAILBODY] = returnArray[EMAILBODY].replaceAll("\\<.*?>", "");
        returnArray[EMAILBODY] = returnArray[EMAILBODY].replaceAll("\\s+", " ");
        returnArray[EMAILBODY] = replaceSpecialCharacters(returnArray[EMAILBODY].replaceAll("&nbsp;", "\n"));
        return returnArray;
    }

    private static String replaceSpecialCharacters(String specialCharacter) {
        specialCharacter = specialCharacter.replaceAll("&aacute;", "á");
        specialCharacter = specialCharacter.replaceAll("&eacute;", "é");
        specialCharacter = specialCharacter.replaceAll("&iacute;", "í");
        specialCharacter = specialCharacter.replaceAll("&oacute;", "ó");
        specialCharacter = specialCharacter.replaceAll("&uacute;", "ú");
        specialCharacter = specialCharacter.replaceAll("&Eacute;", "É");
        specialCharacter = specialCharacter.replaceAll("&Oacute;", "Ó");
        specialCharacter = specialCharacter.replaceAll("&Uacute;", "Ú");
        specialCharacter = specialCharacter.replaceAll("&ntilde;", "ñ");
        specialCharacter = specialCharacter.replaceAll("&Ntilde;", "Ñ");
        return specialCharacter;
    }

    private static String[] parseMessage(Message message) {
        String[] messageParsed = new String[3];
        String stringContent = null;
        try {
            messageParsed[DE] = message.getFrom()[0].toString();
            messageParsed[SUBJECT] = message.getSubject();
            Object content = message.getContent();
            if (message.isMimeType("text/plain")) {
                stringContent = message.getContent().toString();
            } else if (message.isMimeType("text/html")) {
                String html = (String) message.getContent();
                stringContent = org.jsoup.Jsoup.parse(html).text();
            } else if (content instanceof MimeMultipart) {
                Multipart multipart = (Multipart) content;
                stringContent = "MULTIPART;";
                if (multipart.getCount() > 2)
                    Assert.fail("Unknown Mail Format.");
                for (int j = 0; j < multipart.getCount(); j++) {
                    BodyPart bodyPart = multipart.getBodyPart(j);
                    String disposition = bodyPart.getDisposition();
                    if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) {
                        DataHandler handler = bodyPart.getDataHandler();
                        stringContent += "ATTACHMENT: " + handler.getName() + ";";
                    } else {
                        stringContent += "BODY: " + bodyPart.getContent().toString() + ";";
                    }
                }
            }
            messageParsed[EMAILBODY] = stringContent;
        } catch (Exception e) {
            System.err.println("MessagingException: " + e.getMessage());
            Assert.fail("Couldn't parse mail.");
        }
        return messageParsed;
    }
}