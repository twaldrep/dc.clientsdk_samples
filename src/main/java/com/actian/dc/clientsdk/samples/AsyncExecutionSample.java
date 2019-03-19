package com.actian.dc.clientsdk.samples;

import com.pervasive.di.client.sdk.ExecutionConnection;
import com.pervasive.di.client.sdk.Job;
import com.pervasive.di.client.sdk.SDKException;
import com.pervasive.di.client.sdk.Task;

/**
 * Executes a task asynchronously, using polling to determine when task is complete.
 */
public class AsyncExecutionSample extends ExecutionConnectionUser
{
    /**
     * Execute a job asynchronously and determine completion by polling a JobListener
     * instance every 3 seconds for completion
     * @param cxn ExecutionConnection instance 
     * @return true if the job completed successfully, false otherwise
     * @throws SDKException if an unexpected error occurs
     */
    @Override
    public boolean useConnection(ExecutionConnection cxn) throws SDKException
    {
        // Create a new Task and feed it the RTC
        Task task = SamplesRunner.sampleTask("Samples.map.rtc");

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
