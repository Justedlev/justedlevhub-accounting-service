package com.justedlev.hub.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@UtilityClass
public class Constants {
    public static final String BOT_NAME = "justedlev.hub BOT";
    public static final UUID BOT_ID = UUID.nameUUIDFromBytes(BOT_NAME.getBytes(UTF_8));
}
