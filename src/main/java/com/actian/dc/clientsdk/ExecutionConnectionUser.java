package com.actian.dc.clientsdk;

import com.pervasive.di.client.sdk.ExecutionConnection;
import com.pervasive.di.client.sdk.Job;
import com.pervasive.di.client.sdk.JobServiceReturnCode;
import com.pervasive.di.client.sdk.SDKException;
import java.util.logging.Logger;

/**
 * Common base type for execution samples
 * Handles connection setup and cleanup
 */
abstract class ExecutionConnectionUser implements ConnectionUser
{
    protected Logger logger;
    
    @Override
    public boolean supportsLocal() {
        return true;
    }
    
    abstract boolean useConnection(ExecutionConnection cxn) throws SDKException;
        
    @Override
    public boolean useConnection(ConnectionBuilder cxnBuilder)
    {
        logger = LogUtil.getLogger(getClass());

        ExecutionConnection cxn = null;
        try
        {
            cxn = cxnBuilder.createExecutionConnection();            
            return useConnection(cxn);         
        }
        catch (SDKException e) {
            logger.severe(e.getMessage());
            return false;
        }
        finally {
            // disconnect from the service
            if (cxn != null) {
                cxn.disconnect();
            }
        }
    }
    
    protected boolean reportResult(Job job, ExecutionConnection cxn) 
    {
        // Display any Error Message
        if (job.getResult().getErrorMessage() != null)
            logger.info(job.getResult().getErrorMessage());

        try {
            // Get the job log
            logger.info(cxn.getLog(job));
            return (job.getResult().getServiceReturnCode() == JobServiceReturnCode.SUCCEEDED);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return false;
        }
    }
}
