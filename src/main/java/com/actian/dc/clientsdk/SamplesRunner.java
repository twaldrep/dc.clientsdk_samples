package com.actian.dc.clientsdk;

import com.pervasive.di.client.sdk.SDKException;
import com.pervasive.di.client.sdk.Task;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SamplesRunner 
{
    private static final Logger logger = LogUtil.getLogger(SamplesRunner.class);
        
    static final String artifactsPath = new File("artifacts").getAbsolutePath();
     
    static String artifactPath(String name) {
        return artifactsPath+"/"+name;
    }
    
    static final String samplePackageName = "Samples";
    static final String samplePackageVersion = "1.0";
           
    static String samplePackagePath() {
        String fullPackageName = samplePackageName + "-" + samplePackageVersion + ".djar";
        return artifactPath(fullPackageName);
    }
    
    private static final String sampleDataMacroName = "samples";
    
    //private static final String sampleDataMacroValue = "C:/data/samples";
    private static final String sampleDataMacroValue = new File("data").getAbsolutePath();
              
    private static TaskBuilder taskBuilder;
    
    static {
        Map<String, String> macros = new HashMap<String, String>();
        macros.put(sampleDataMacroName, sampleDataMacroValue);        
        taskBuilder = new TaskBuilder(samplePackageName, samplePackageVersion, macros);
    }
    
    static Task sampleTask(String rtcName) throws SDKException {
        if (rtcName == null) {
            return taskBuilder.buildTask();
        }
        else {
            File rtcFile = new File(artifactPath(rtcName+".configuration"));                
            Task task = taskBuilder.buildTask(rtcFile);
            task.setName("Run sample process using configuration "+rtcName);
            return task;
        }        
    }
    
    public static void main(String[] args) throws Exception
    {
        List<ConnectionUser> samples = new ArrayList<ConnectionUser>();
        samples.add(new DeploymentSample());        
        samples.add(new SyncExecutionSample());
        samples.add(new AsyncExecutionSample());
        samples.add(new ThreadedAsyncExecutionSample());
        samples.add(new ExecutionListenerSample());
        samples.add(new SchedulingSample());
        
        ConnectionBuilder cxnBuilder = new ConnectionBuilder();        
        for (ConnectionUser sample : samples) {
            if (!cxnBuilder.isLocal() || sample.supportsLocal()) {
                String sampleName = sample.getClass().getSimpleName();
                logger.info("Starting " + sampleName);
                boolean ok = sample.useConnection(cxnBuilder);
                String status = ok ? "OK" : "ERROR";
                logger.info(sampleName + " finished " +status+"\n");
                if (!ok) {
                    break;
                }
            }            
        }
    }
}
