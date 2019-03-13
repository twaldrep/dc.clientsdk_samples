package com.actian.dc.clientsdk.samples;

import com.pervasive.di.client.sdk.JobListener;
import com.pervasive.di.client.sdk.JobProgress;
import java.util.logging.Logger;

public class SimpleJobListener implements JobListener
{
    protected final Logger logger;
    
    private boolean finished = false;
    
    public SimpleJobListener(Logger logger) {
        this.logger=logger;
    }

    @Override
    public synchronized void jobProgress(JobProgress progress)
    {
        switch (progress.getJobStatusCode())
        {
        case QUEUED:
            logger.info("Queued Job ID: "+progress.getJobId());
            break;
        case RUNNING:
            logger.info("Running Job ID: "+progress.getJobId());
            break;
        case FINISHED_OK: 
            logger.info("Completed Succesfully Job ID: "+progress.getJobId()); 
            finished = true;
            break;
        case FINISHED_ERROR:
            logger.info("Completed Unsuccesfully Job ID: "+progress.getJobId());
            finished = true;
            break;
        case ABORTED:
            logger.info("Aborted Job ID: "+progress.getJobId());
            finished = true;
            break;
        }
    }
    
    synchronized boolean isFinished() {
        return finished;
    }
}
