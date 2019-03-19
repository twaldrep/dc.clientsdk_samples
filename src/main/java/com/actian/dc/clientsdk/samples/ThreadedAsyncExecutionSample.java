package com.actian.dc.clientsdk.samples;

import com.pervasive.di.client.sdk.*;
import java.util.logging.Logger;

/**
 * Executes a task asynchronously, using wait/notify to determine when the task is complete.
 */
public class ThreadedAsyncExecutionSample extends ExecutionConnectionUser
{
    /**
     * @see com.actian.dc.clientsdk.samples.ExecutionConnectionUser#useConnection(com.pervasive.di.client.sdk.ExecutionConnection) 
     */
    @Override
    public boolean useConnection(ExecutionConnection cxn) throws SDKException
    {
        // Create a new Task and feed it the RTC
        Task task = SamplesRunner.sampleTask("Samples.process.rtc");
        logger.info("Submitting task "+task.getTaskName());
        JobListener listener = new Listener(logger);
        Job job = null;

        // Wait until the listener says it's ok to proceed
        try {
            synchronized(this) {
                job = cxn.submit(task, listener);
                wait();
            }
        } catch (InterruptedException e) {
        }

        // Report results
        return reportResult(job, cxn);
    }
    
    /**
     * Custom com.pervasive.di.client.sdk.JobListener instance which overrides
     * the jobProgress method to notify the waiting thread that the job has finished.
     */
    public class Listener extends SimpleJobListener
    { 
        public Listener(Logger logger) {
            super(logger);
        }

        /**
         * Overridden to notify the waiting thread to wake up and finish.
         * @see com.actian.dc.clientsdk.samples.SimpleJobListener#jobProgress(com.pervasive.di.client.sdk.JobProgress) 
         */
        @Override
        public void jobProgress(JobProgress progress) {
            super.jobProgress(progress);
            if (isFinished()) {                
                synchronized(ThreadedAsyncExecutionSample.this) {
                    ThreadedAsyncExecutionSample.this.notify();
                }
            }
        }
    }
}
