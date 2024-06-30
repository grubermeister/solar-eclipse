package dev.atomixsoft.solar_eclipse.client.config;

import dev.atomixsoft.solar_eclipse.core.config.*;


public class Configuration {
    private String NAME_VAR;
    private String USER_VAR, PASS_VAR, SAVE_PASS_VAR;
    private String IP_VAR, PORT_VAR;
    private String MENU_MUSIC_VAR;
    private String MUSIC_VAR, SOUND_VAR;
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

            this.NAME_VAR = this.m_ConfigFile.getValue("client.sGameName");
            this.USER_VAR = this.m_ConfigFile.getValue("client.sUsername");
            this.PASS_VAR = this.m_ConfigFile.getValue("client.sPassword");
            this.SAVE_PASS_VAR = this.m_ConfigFile.getValue("client.bSavePass");
            this.IP_VAR = this.m_ConfigFile.getValue("client.sIP");
            this.PORT_VAR = this.m_ConfigFile.getValue("client.iPort");
            this.MENU_MUSIC_VAR = this.m_ConfigFile.getValue("client.sMenuMusic");
            this.MUSIC_VAR = this.m_ConfigFile.getValue("client.bMusic");
            this.SOUND_VAR = this.m_ConfigFile.getValue("client.bSound");
            this.DEBUG_VAR = this.m_ConfigFile.getValue("client.bDebug");

            this.LOG_LEVEL_VAR = this.m_ConfigFile.getValue("logging.sLevel");
            this.LOG_PATTERN_VAR = this.m_ConfigFile.getValue("logging.sPattern");
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not load the configuration file");
        }
    }

    public String getGameName() {
        return this.NAME_VAR;
    }

    public String getUsername() {
        return this.USER_VAR;
    }

    public String getPassword() {
        return this.PASS_VAR;
    }

    public String getSavePass() {
        return this.SAVE_PASS_VAR;
    }

    public String getIP() {
        return this.IP_VAR;
    }

    public String getPort() {
        return this.PORT_VAR;
    }

    public String getMenuMusic() {
        return this.MENU_MUSIC_VAR;
    }

    public String getMusic() {
        return this.MUSIC_VAR;
    }

    public String getSound() {
        return this.SOUND_VAR;
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
