package Sender;

import Detector.ChangeDetector;
import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;


public class MailSender implements Sender
{
    private HashMap<URL, String> yesTDPages;
    private HashMap<URL, String> TDPages;
    private String initialsOfManager;
    private String fromAddress;
    private String toAddress;

    private String smtp_server = "smtp.gmail.com";
    private String server_port = "587";
    private String login;
    private String password;

    private MailSender(BuilderMS builderMS)
    {
        initialsOfManager = builderMS.initialsOfManager;
        fromAddress = builderMS.fromAddress;
        toAddress =   builderMS.toAddress;
        login = builderMS.login;
        password = builderMS.password;
    }

    public MailSender setHashTables(HashMap<URL, String> yesTDHtml, HashMap<URL, String>TDHtml)
    {
        Objects.requireNonNull(this.yesTDPages = yesTDHtml);
        Objects.requireNonNull(this.TDPages = TDHtml);
        return this;
    }

    private Properties createPropertieSession()
    {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", smtp_server);
        prop.put("mail.smtp.port", server_port);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        return prop;
    }

    @Override
    public void send()
    {
        Session session = Session.getInstance(createPropertieSession(), null);

        try
        {
            MailMessage message = new MailMessage(session);
            Message mes = message.createMessage();
            SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
            transport.connect(smtp_server, login, password);
            transport.sendMessage(mes, mes.getAllRecipients());
            transport.close();
        }
        catch (MessagingException ex)
        {
            ex.printStackTrace();
        }
    }

    public static class BuilderMS
    {
        private String initialsOfManager;
        private String fromAddress;
        private String toAddress;
        private String login;
        private String password;

        public BuilderMS setInitials(String initials)
        {
            Objects.requireNonNull(this.initialsOfManager = initials);
            return this;
        }

        public BuilderMS setFromAddress(String fromAddress) throws IOException
        {
            Objects.requireNonNull(this.fromAddress = fromAddress);
            if (fromAddress.equals(""))
                throw new IOException("need to point from-address!");

            return this;
        }

        public BuilderMS setToAddress(String toAddress) throws IOException {
            Objects.requireNonNull(this.toAddress = toAddress);
            if (fromAddress.equals(""))
                throw new IOException("need to point to-address!");

            return this;
        }

        public BuilderMS setAuthenticationOptions(String login, String password) throws IOException {
            Objects.requireNonNull(this.login = login);
            Objects.requireNonNull(this.password = password);

            if (login.equals("") || password.equals(""))
                throw new IOException("password or login can't be empty!");

            return this;
        }

        public MailSender build()
        {
            return new MailSender(this);
        }

    }

    private class MailMessage
    {
        private final String GREETING = "Здравствуйте, дорогая ";
        private final String INTRODUCTION = "За последние сутки во вверенных Вам сайтах произошли следующие изменения: \n";
        private final String DELETED_PAGES = "Исчезли следующие страницы: \n";
        private final String NEW_PAGES = "Появились следующие новые страницы: \n";
        private final String CHANGED_PAGES = "Изменились следующие страницы: \n";
        private final String CONCLUSION = "С уважением,\n" + "автоматизированная система\n" + "мониторинга.";
        private final String SUBJECT = "website monitoring";

        private Message mes;

        MailMessage(Session session) throws MessagingException
        {
            mes = new MimeMessage(session);
            mes.setFrom(new InternetAddress(fromAddress));

            mes.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddress, false));
            mes.setSubject(SUBJECT);
        }

        private String generateText()
        {
            StringBuilder builder = new StringBuilder();
            builder.append(GREETING).append(initialsOfManager).append("\n");
            builder.append(INTRODUCTION);
            builder.append(DELETED_PAGES);
            HashMap<URL, String> deletedURL = ChangeDetector.findDeletedUrls(yesTDPages, TDPages);
            deletedURL.keySet().forEach(x -> builder.append(x).append('\n'));

            builder.append(NEW_PAGES);
            HashMap<URL, String> newURL = ChangeDetector.findAddedUrls(yesTDPages, TDPages);
            newURL.keySet().forEach(x -> builder.append(x).append('\n'));

            builder.append(CHANGED_PAGES);
            HashMap<URL, String> changedURL = ChangeDetector.findChangedUrls(yesTDPages, TDPages);
            changedURL.keySet().forEach(x -> builder.append(x).append('\n'));

            builder.append(CONCLUSION);
            return builder.toString();
        }

        Message createMessage() throws MessagingException
        {
            mes.setText(generateText());
            mes.setSentDate(new Date());
            return mes;
        }
    }
}