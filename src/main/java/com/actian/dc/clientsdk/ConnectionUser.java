package com.actian.dc.clientsdk;

public interface ConnectionUser
{    
    boolean supportsLocal();
    
    boolean useConnection(ConnectionBuilder cxnBuilder);
}
