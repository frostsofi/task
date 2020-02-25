package Jobs;

import Sender.MailSender;
import Sender.MailSenderConfig;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class MonitoringJob implements Job
{
    @Override
    public void execute(JobExecutionContext context)   //in this class we can upload maps from database (for yesterday) and upload through jsoup(today)
    {
        HashMap<URL, String> yesTDPages = new HashMap<>();
        HashMap<URL, String> TDPages = new HashMap<>();
        MailSender mailSender = null;

        try
        {
            mailSender = MailSenderConfig.getConfiguredMS("config");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            yesTDPages.put(new URL("https://table.nsu.ru/group/17201"), "<td><b>1 пара</b></td>");
            yesTDPages.put(new URL("https://table.nsu.ru/group/17202"), "<td><b>2 пара</b></td>");
            yesTDPages.put(new URL("https://table.nsu.ru/group/17203"), "<td><b>3 пара</b></td>");
            yesTDPages.put(new URL("https://table.nsu.ru/group/17204"), "<td><b>4 пара</b></td>");
            yesTDPages.put(new URL("https://table.nsu.ru/group/17205"), "<td><b>5 пара</b></td>");


            TDPages.put(new URL("https://table.nsu.ru/group/17201"), "<td><b>1 пара</b></td>");
            TDPages.put(new URL("https://table.nsu.ru/group/17203"), "<td><b>1222 пара</b></td>");
            TDPages.put(new URL("https://table.nsu.ru/group/17205"), "<td><b>1221 пара</b></td>");
            TDPages.put(new URL("https://table.nsu.ru/group/17206"), "<td><b>6 пара</b></td>");
            TDPages.put(new URL("https://table.nsu.ru/group/17207"), "<td><b>7 пара</b></td>");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        assert mailSender != null;
            mailSender.setHashTables(yesTDPages, TDPages).send();
    }
}