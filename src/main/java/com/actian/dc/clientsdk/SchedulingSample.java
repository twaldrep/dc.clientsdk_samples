package com.actian.dc.clientsdk;

import com.pervasive.di.client.sdk.*;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

/**
 * Creates and submits a schedule for asynchronously executing tasks
 */
public class SchedulingSample implements ConnectionUser 
{    
    private URL scheduledURL;
    
    @Override
    public boolean supportsLocal() {
        return false;
    }
    
    @Override
    public boolean useConnection(ConnectionBuilder cxnBuilder)
    {
        Logger logger = LogUtil.getLogger(SchedulingSample.class);

        SchedulingConnection cxn = null;
        try
        {
            cxn = cxnBuilder.createSchedulingConnection();
        		
            // Create a new Task and feed it the RTC
            Task task = SamplesRunner.sampleTask("map");       
            
            // Build a TaskSchedule to create a Schedule in the ExecutionService
            TaskSchedule taskSchedule = new TaskSchedule();
            taskSchedule.setTask(task);
            taskSchedule.setDescription("This is a sample schedule");
            Calendar calendar = new GregorianCalendar();
            String startDate = TaskSchedule.dateToString(calendar.getTime());
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 3);
            String endDate = TaskSchedule.dateToString(calendar.getTime());
            String recurrenceType = "SECONDS";
            Set<Integer> periods =  new HashSet();
            periods.add(15);
            taskSchedule.createRecurrence(recurrenceType, startDate, endDate, periods);
            taskSchedule.setState("ACTIVE");
            
            // Create the schedule in the execution service
            // The jobProgress method will get called as each job runs
            // exit when it returns no more scheduled jobs for the schedule
            logger.info("Executing Package '"+task.getPackageName()+"' Version '"+task.getPackageVersion()+"'");
            JobListener listener = new SimpleJobListener(logger);
            scheduledURL = cxn.createSchedule(taskSchedule, listener);
                        
            ScheduledJobs scheduledJobs=null;
            int count=0;
            do
            {
                Calendar cal = new GregorianCalendar();
                cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
                scheduledJobs = cxn.getScheduledJobs(scheduledURL, new Date(), cal.getTime(), 100);
                
                if (scheduledJobs.getJobStartTimes() != null) {
                    count = scheduledJobs.getJobStartTimes().size();
                    System.out.println("Schedule " + getID(scheduledURL) + " has " + count + " jobs scheduled");
                }
                waitfor(10000);
            } while (scheduledJobs.getJobStartTimes() != null && count > 0);
            
            System.err.println("No more scheduled jobs, deleting schedule " + getID(scheduledURL));
            
            // delete the schedule when it has no more scheduled jobs    
            cxn.deleteSchedule(scheduledURL, true, true);
            Schedule schedule = cxn.getSchedule(scheduledURL);
            if (schedule != null) {
                logger.info("Still jobs for deleted schedule " + scheduledURL.toString());
            }
            else {
                logger.info("Schedule " + getID(scheduledURL) + " not found");
            }
            return true;
        }
        catch (SDKException e)
        {
            logger.severe(e.getMessage());
        }
        finally {
            if (cxn != null) {
                cxn.disconnect();
            }
        }
        return false;
    }
    
    private static void waitfor(long milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (Exception e)
        {

        }
    }
    
    private static String getID(URL url)
    {
        return url.toString().substring(url.toString().lastIndexOf('/')+1);
    }
}
