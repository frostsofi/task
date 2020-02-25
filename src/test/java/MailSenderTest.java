import Sender.MailSender;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.HashMap;

public class MailSenderTest
{
    private MailSender.BuilderMS builderMS = new MailSender.BuilderMS();
    private MailSender mailSender;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void setInitials()
    {
        thrown.expect(NullPointerException.class);
        builderMS.setInitials(null);
    }

    @Test
    public void setFromAddress_1() throws IOException
    {
        thrown.expect(NullPointerException.class);
        builderMS.setFromAddress(null);
    }

    @Test
    public void setFromAddress_2() throws IOException
    {
        thrown.expect(IOException.class);
        builderMS.setFromAddress("");
    }

    @Test
    public void setToAddress_1() throws IOException
    {
        thrown.expect(NullPointerException.class);
        builderMS.setToAddress(null);
    }

    @Test
    public void setToAddress_2() throws IOException
    {
        thrown.expect(NullPointerException.class);
        builderMS.setToAddress("");
    }

    @Test
    public void setAuthenticationOptions() throws IOException
    {
        thrown.expect(IOException.class);
        builderMS.setAuthenticationOptions("*", "");

        thrown.expect(IOException.class);
        builderMS.setAuthenticationOptions("", "*");
    }

    @Test
    public void setHashTables()
    {
        mailSender = builderMS.build();
        thrown.expect(NullPointerException.class);
        mailSender.setHashTables(null, null);

        thrown.expect(NullPointerException.class);
        mailSender.setHashTables(new HashMap<>(), null);

        thrown.expect(NullPointerException.class);
        mailSender.setHashTables(null, new HashMap<>());
    }
}