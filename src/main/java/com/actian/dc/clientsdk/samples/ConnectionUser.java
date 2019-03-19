package com.actian.dc.clientsdk.samples;

/**
 * Base interface implemented by all execution samples
 */
public interface ConnectionUser
{    
    /**
     * Does this sample support local execution?
     * @return true if the sample supports local execution, false otherwise
     */
    boolean supportsLocal();
    
    /**
     * Called to create, and track completion of a job using the provided ConnectionBuilder
     * instance
     * @param cxnBuilder instance of ConnectionBuilder
     * @return true if the job completed successfully, false otherwise
     */
    boolean useConnection(ConnectionBuilder cxnBuilder);
}
