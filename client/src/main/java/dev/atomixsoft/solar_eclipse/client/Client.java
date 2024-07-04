package dev.atomixsoft.solar_eclipse.client;

import dev.atomixsoft.solar_eclipse.client.config.Configuration;
import dev.atomixsoft.solar_eclipse.client.config.Configuration.SupportedConfigFileTypes;
import dev.atomixsoft.solar_eclipse.client.logging.Logger;
import dev.atomixsoft.solar_eclipse.client.logging.Logger.SupportedLogHandlerTypes;


public class Client {
    public static void main(String[] args) {
        Configuration configInfo = new Configuration(SupportedConfigFileTypes.INI, "client/client.ini");
        Logger logger = new Logger(configInfo.getGameName() + " - Client", 
                                   SupportedLogHandlerTypes.ASYNC_CONSOLE, 
                                   configInfo.getLogLevel(),
                                   configInfo.getLogPattern());
        final ClientThread eclipse = new ClientThread(configInfo.getGameName(), logger);

        logger.debug("Spinning up threads...");
       
        eclipse.start();
    }
}
