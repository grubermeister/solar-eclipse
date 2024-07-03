module dev.atomixsoft.solar_eclipse.core {
    requires org.apache.commons.configuration2;

    opens dev.atomixsoft.solar_eclipse.core.config to org.apache.commons.configuration2;

    exports dev.atomixsoft.solar_eclipse.core.game;
    exports dev.atomixsoft.solar_eclipse.core.game.map;
    exports dev.atomixsoft.solar_eclipse.core.game.character;

    exports dev.atomixsoft.solar_eclipse.core.utils;
    exports dev.atomixsoft.solar_eclipse.core.config;
    //exports dev.atomixsoft.solar_eclipse.core.network;
}
