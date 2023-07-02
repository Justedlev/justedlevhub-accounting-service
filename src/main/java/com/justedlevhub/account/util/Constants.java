package com.justedlevhub.account.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String BOT_NAME = "Justedlev BOT";
    public static final UUID BOT_ID = UUID.nameUUIDFromBytes(BOT_NAME.getBytes(StandardCharsets.UTF_8));
    public static final String X_REMOTE_USER = "X-Remote-User";
}
