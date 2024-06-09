package dev.atomixsoft.solar_eclipse.client;

import dev.atomixsoft.solar_eclipse.client.config.Configuration;


public class Client {
    public static void main(String[] args) {
        Configuration configInfo = new Configuration(Configuration.SupportedConfigFileTypes.INI, "client/client.ini");
        final ClientThread eclipse = new ClientThread(configInfo.NAME_VAR);

        eclipse.start();
    }
}
