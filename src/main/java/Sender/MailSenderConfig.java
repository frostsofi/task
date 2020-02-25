package Sender;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class MailSenderConfig
{
    private MailSenderConfig(){};

    public static MailSender getConfiguredMS(String propName) throws IOException
    {
        Properties config = new Properties();
        config.load(Objects.requireNonNull(MailSender.class.getClassLoader().getResourceAsStream(propName)));

        return new MailSender.BuilderMS()
                .setAuthenticationOptions(config.getProperty("login"), config.getProperty("password"))
                .setFromAddress(config.getProperty("fromAddr"))
                .setToAddress(config.getProperty("toAddr"))
                .setInitials(config.getProperty("initialsManager"))
                .build();
    }
}