package com.actian.dc.clientsdk;

import com.pervasive.di.client.sdk.*;
import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionBuilder
{
    private static final Logger logger = LogUtil.getLogger(ConnectionBuilder.class);

    // Local or remote execution?

    private static final ConnectionType connectionType = ConnectionType.REMOTE;
    //private static final ConnectionType connectionType = ConnectionType.LOCAL;

    // Configuration for remote execution

    private static final String server = "http://localhost";
    private static final String username = "super";
    private static final String password = "super";

    // Configuration for local execution

    private static final String installPath = "C:/Actian/di-standalone-engine-64bit-10.2.7-38/runtime/di9";
	//private static final String installPath = "/opt/Actian/di-standalone-engine-10.2.7-38/runtime/di9";
    private static final String listenerPort = "4443";
    private static final String workingDir = "work";
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
        Properties props = new Properties();
        props.put(ConnectionFactory.CONNECTIONTYPE, connectionType.toString());
        switch (connectionType) {
        case LOCAL:
            props.put(ConnectionFactory.LOCAL_ENGINE_INSTALL_PATH, installPath);
            props.put(ConnectionFactory.LOCAL_ENGINE_LISTENER_PORT, listenerPort);
            props.put(ConnectionFactory.LOCAL_WORK_DIRECTORY, workingDir());
            props.put(ConnectionFactory.PACKAGELOCATION, packageLocation);
            break;
        case REMOTE:
            props.put(ConnectionFactory.SERVER, server);
            props.put(ConnectionFactory.USERNAME, username);
            props.put(ConnectionFactory.PASSWORD, password);
            break;
        }
        return props;
    }

    private static String workingDir() {
        File f = new File(workingDir);
        f.mkdirs();
        return f.getAbsolutePath();
    }
}
