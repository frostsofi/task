import Jobs.MonitoringJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.newJob;

public class Main
{
    public static void main(String[] args) throws SchedulerException
    {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail job = newJob(MonitoringJob.class).build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger", "group")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(12, 30))
                .build();

        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}