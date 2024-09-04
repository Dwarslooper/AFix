package com.dwarslooper.mods.afix;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AFixClient implements ClientModInitializer {

    public static AFixClient INSTANCE;
    public static Logger LOGGER = LoggerFactory.getLogger(AFixClient.class);

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("Loaded AFix");
    }

}
