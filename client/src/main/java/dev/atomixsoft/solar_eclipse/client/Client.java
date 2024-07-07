package dev.atomixsoft.solar_eclipse.client;

import dev.atomixsoft.solar_eclipse.client.config.Configuration;
import dev.atomixsoft.solar_eclipse.client.config.Configuration.SupportedConfigFileTypes;
import dev.atomixsoft.solar_eclipse.client.util.logging.Logger;
import dev.atomixsoft.solar_eclipse.client.util.logging.Logger.SupportedLogHandlerTypes;


public class Client {
    public static void main(String[] args) {
        Configuration configInfo = new Configuration(SupportedConfigFileTypes.INI, "client/client.ini");
        Logger logger = new Logger(Client.class.getSimpleName(), 
                                   SupportedLogHandlerTypes.ASYNC_CONSOLE, 
                                   configInfo.getLogLevel(),
                                   configInfo.getLogPattern());
        final ClientThread eclipse = new ClientThread(configInfo.getGameName(), logger);

        logger.debug("Client started.");
        logger.debug("Spinning up threads...");
        try {
            eclipse.start();
        } catch (Exception e) {
            logger.error("Failed to start Frontend thread!");
            logger.error(e.getMessage());
        }

        while(eclipse.isRunning()) { 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Main thread interrupted!");
                logger.error(e.getMessage());
            }
        }

        logger.debug("Client exiting.");
        System.exit(0);
    }
}
