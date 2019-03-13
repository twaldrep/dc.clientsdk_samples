package com.actian.dc.clientsdk.samples;

import com.pervasive.di.artifacts.shared.NameValuePair;
import com.pervasive.di.client.sdk.SDKException;
import com.pervasive.di.client.sdk.Task;
import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

public class TaskBuilder 
{
    private static final Logger logger = LogUtil.getLogger(TaskBuilder.class);
            
    private final String packageName;
    private final String packageVersion;
    private final Map<String, String> localMacros;
        
    public TaskBuilder(String pkgName, String pkgVersion, Map<String, String> localMacros) {
        this.packageName = pkgName;
        this.packageVersion = pkgVersion;
        this.localMacros = localMacros;
    }
        
    public Task buildTask() throws SDKException {
        return buildTask(null);
    }
    
    public Task buildTask(File rtcFile) throws SDKException {
        logger.info("Creating task for '"+packageName+"' Version '"+packageVersion+"'");
        Task task = new Task(packageName, packageVersion);
        if (rtcFile != null) {
            task.populate(rtcFile);
        }
        for (Map.Entry<String, String> entry : localMacros.entrySet()) {          
            task.addMacro(new NameValuePair(entry.getKey(), entry.getValue()));
        }
        return task;
    }
}
