package dev.atomixsoft.solar_eclipse.client.config;

import dev.atomixsoft.solar_eclipse.core.config.*;


public class Configuration {
    public String NAME_VAR;
    public String USER_VAR, PASS_VAR, SAVE_PASS_VAR;
    public String IP_VAR, PORT_VAR;
    public String MENU_MUSIC_VAR;
    public String MUSIC_VAR, SOUND_VAR;
    public String DEBUG_VAR;

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
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not load the configuration file");
        }
    }
}
