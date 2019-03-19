/*
 * Copyright 2019 Actian Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.actian.dc.clientsdk.samples;

import com.pervasive.di.client.sdk.JobListener;
import com.pervasive.di.client.sdk.JobProgress;
import java.util.logging.Logger;

/**
 * Simple com.pervasive.di.client.sdk.JobListener instance which logs job progress
 * events.
 */
public class SimpleJobListener implements JobListener
{
    protected final Logger logger;
    
    private boolean finished = false;
    
    public SimpleJobListener(Logger logger) {
        this.logger=logger;
    }

    /**
     * Primary callback method for job progress events.
     * @param progress com.pervasive.di.client.sdk.JobProgress instance
     */
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
