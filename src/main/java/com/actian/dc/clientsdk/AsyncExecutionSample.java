package com.actian.dc.clientsdk;

import com.pervasive.di.client.sdk.ExecutionConnection;
import com.pervasive.di.client.sdk.Job;
import com.pervasive.di.client.sdk.SDKException;
import com.pervasive.di.client.sdk.Task;

/**
 * Executes a task asynchronously, using polling to determine when task is complete.
 */
public class AsyncExecutionSample extends ExecutionConnectionUser
{
    @Override
    public boolean useConnection(ExecutionConnection cxn) throws SDKException
    {
        // Create a new Task and feed it the RTC
        Task task = SamplesRunner.sampleTask("map");

        // Execute the task asynchronously.
        // The SimpleJobListener will set the finished boolean to true when the job is done.
        SimpleJobListener listener = new SimpleJobListener(logger);
        logger.info("Submitting task "+task.getTaskName());
        Job job = cxn.submit(task, listener);

        try {
            // Wait until the listener says it's ok to proceed
            while (!listener.isFinished())
                Thread.sleep(3000);
        } catch(InterruptedException e) {
            logger.severe(e.getMessage());
            return false;   
        }
        
        // Report results
        return reportResult(job, cxn);       
    }
}
