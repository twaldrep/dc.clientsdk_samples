package com.actian.dc.clientsdk;

import java.util.logging.*;

public class LogUtil 
{
    static Logger getLogger(Class<?> clazz) {
        // Set the log level to Info and send logs to the console
        Logger logger = Logger.getLogger(clazz.getName());
        logger.setLevel(Level.INFO);
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter()); 
        return logger;
    }
}
