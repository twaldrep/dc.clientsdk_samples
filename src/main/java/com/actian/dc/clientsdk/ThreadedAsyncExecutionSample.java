package com.actian.dc.clientsdk;

import com.pervasive.di.client.sdk.*;
import java.util.logging.Logger;

/**
 * Executes a task asynchronously, using wait/notify to determine when task is complete.
 */
public class ThreadedAsyncExecutionSample extends ExecutionConnectionUser
{
    @Override
    public boolean useConnection(ExecutionConnection cxn) throws SDKException
    {
        // Create a new Task and feed it the RTC
        Task task = SamplesRunner.sampleTask("process");
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
    
    public class Listener extends SimpleJobListener
    { 
        public Listener(Logger logger) {
            super(logger);
        }

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
