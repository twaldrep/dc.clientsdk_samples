package com.actian.dc.clientsdk;

import com.pervasive.di.client.sdk.DeploymentConnection;
import com.pervasive.di.client.sdk.DeploymentInfo;
import com.pervasive.di.client.sdk.SDKException;
import java.io.File;
import java.util.logging.Logger;

/**
 * DeploymentSample - Sends a deployment package to a remote repository. 
 * Run this sample first so that later samples can execute entry points 
 * within the deployment package.
 */
public class DeploymentSample implements ConnectionUser
{
    private static final Logger logger = LogUtil.getLogger(DeploymentSample.class);
    
    private static String packageDescription = "Some Test Project";        
    
    @Override
    public boolean supportsLocal() {
        return false;
    }
    
    @Override
    public boolean useConnection(ConnectionBuilder cxnBuilder)
    {
        DeploymentConnection cxn = null;
        try
        {
            // Init the Connection
            cxn = cxnBuilder.createDeploymentConnection();

            // Deploy a project for execution
            String packageName = SamplesRunner.samplePackageName;
            String packageVersion = SamplesRunner.samplePackageVersion;
            String packageFile = SamplesRunner.samplePackagePath();
            logger.info("Deploy File: "+packageFile+" as Package: '"+packageName+"' Version: '"+packageVersion+"'");
            cxn.deploy(packageName, packageVersion, packageDescription, new File(packageFile));

            // See which packages are deployed
            logger.info("List Currently Deployment Packages.");
            for (DeploymentInfo di : cxn.getPackagesInfo()) {
                logger.info("   Name: "+di.getName()+" Version "+di.getVersion());
                logger.info("        Deployed "+di.getDeployDate());
            }
            return true;
        }
        catch (SDKException e) {
            logger.severe(e.getMessage());
        }
        finally {
            if (cxn != null) {
                cxn.disconnect();
            }
        }
        return false;
    }
}
