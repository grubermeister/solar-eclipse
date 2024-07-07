package dev.atomixsoft.solar_eclipse.server.config;

import dev.atomixsoft.solar_eclipse.core.config.*;


public class Configuration {
    private String NAME_VAR;
    private String MOTD_VAR;
    private String IP_VAR;
    private Integer PORT_VAR;
    private String DEBUG_VAR;

    public String LOG_LEVEL_VAR, LOG_PATTERN_VAR;

    private ConfigurationFile m_ConfigFile;

    public enum SupportedConfigFileTypes {
        INI
    }


    public Configuration(SupportedConfigFileTypes fileType ) {
        switch(fileType) {
            case SupportedConfigFileTypes.INI:
                this.m_ConfigFile = new INIConfigurationFile();

                break;
            default:
                throw new IllegalArgumentException("Unsupported configuration file type");
        }
    }

    public Configuration(SupportedConfigFileTypes fileType, String path) {
        this(fileType);

        try {
            this.m_ConfigFile.load(path);

            this.NAME_VAR = this.m_ConfigFile.getValue("server.sGameName");
            this.MOTD_VAR = this.m_ConfigFile.getValue("server.sMotD");
            this.IP_VAR = this.m_ConfigFile.getValue("server.sIP");
            this.PORT_VAR = Integer.parseInt(this.m_ConfigFile.getValue("server.iPort"));
            this.DEBUG_VAR = this.m_ConfigFile.getValue("server.bDebug");

            this.LOG_LEVEL_VAR = this.m_ConfigFile.getValue("logging.sLevel");
            this.LOG_PATTERN_VAR = this.m_ConfigFile.getValue("logging.sPattern");
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not load the configuration file");
        }
    }

    public String getGameName() {
        return this.NAME_VAR;
    }
    
    public String getIP() {
        return this.IP_VAR;
    }

    public Integer getPort() {
        return this.PORT_VAR;
    }

    public String getDebug() {
        return this.DEBUG_VAR;
    }

    public String getLogLevel() {
        return this.LOG_LEVEL_VAR;
    }

    public String getLogPattern() {
        return this.LOG_PATTERN_VAR;
    }
}
