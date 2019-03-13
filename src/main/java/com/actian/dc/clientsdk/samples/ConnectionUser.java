package com.actian.dc.clientsdk.samples;

public interface ConnectionUser
{    
    boolean supportsLocal();
    
    boolean useConnection(ConnectionBuilder cxnBuilder);
}
