package com.actian.dc.clientsdk.samples;

//import com.pervasive.di.client.sdk.*;
import com.pervasive.di.client.sdk.ConnectionFactory;
import com.pervasive.di.client.sdk.ConnectionType;
import com.pervasive.di.client.sdk.DeploymentConnection;
import com.pervasive.di.client.sdk.ExecutionConnection;
import com.pervasive.di.client.sdk.SDKException;
import com.pervasive.di.client.sdk.SchedulingConnection;
import com.pervasive.cosmos.Config;
import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionBuilder
{
    private static final Logger logger = LogUtil.getLogger(ConnectionBuilder.class);

    // Local or remote execution?

    private static final ConnectionType connectionType = ConnectionType.LOCAL;
    //private static final ConnectionType connectionType = ConnectionType.LOCAL;

    // Configuration for local execution

    private static final String installPath = Config.getInstance().getProperty("InstallPath");
    private static final String iniFilePath = Config.getInstance().getIniFile().getAbsolutePath();
    private static final String listenerPort = "4443";
    private static final String workingDir = "target/work";
    private static final String packageLocation = SamplesRunner.artifactsPath;

    private final ConnectionFactory factory;

    public ConnectionBuilder() {
        factory = createFactory();
    }

    boolean isLocal() {
        return ConnectionBuilder.connectionType == ConnectionType.LOCAL;
    }

    public ExecutionConnection createExecutionConnection() throws SDKException {
        logger.info("Creating ExecutionConnection");
        if (isLocal()) {
            return factory.createLocalConnection();
        }
        else {
            return factory.createExecutionConnection();
        }
    }

    public DeploymentConnection createDeploymentConnection() throws SDKException {
        logger.info("Creating DeploymentConnection");
        return factory.createDeploymentConnection();
    }

    public SchedulingConnection createSchedulingConnection() throws SDKException {
        logger.info("Creating SchedulingConnection");
        return factory.createSchedulingConnection();
    }

    private static ConnectionFactory createFactory() {
        Properties props = getConfiguration();
        logConfiguration(props);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setProperties(props);
        return connectionFactory;
    }

    private static void logConfiguration(Properties props) {
        logger.info("Configuring factory");
        Enumeration e = props.propertyNames();
        while (e.hasMoreElements()) {
            String propName = (String)e.nextElement();
            String propVal = props.getProperty(propName);
            logger.info(propName + " = " + propVal);
        }
        logger.info("End factory configuration\n");
    }

    private static Properties getConfiguration() {
        if (connectionType != ConnectionType.LOCAL)
            throw new IllegalStateException(
                    "ConnectionFactory can't be created with non-local connection type.");
        Properties props = new Properties();
        props.put(ConnectionFactory.CONNECTIONTYPE, connectionType.toString());
        props.put(ConnectionFactory.LOCAL_ENGINE_INSTALL_PATH, installPath);
        props.put(ConnectionFactory.INI_FILE_PATH,iniFilePath);
        props.put(ConnectionFactory.LOCAL_ENGINE_LISTENER_PORT, listenerPort);
        props.put(ConnectionFactory.LOCAL_WORK_DIRECTORY, workingDir());
        props.put(ConnectionFactory.PACKAGELOCATION, packageLocation);

        return props;
    }

    private static String workingDir() {
        File f = new File(workingDir);
        f.mkdirs();
        return f.getAbsolutePath();
    }
}
