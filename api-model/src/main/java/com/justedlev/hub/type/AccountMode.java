package com.justedlev.hub.type;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMode {
    private static final String PREFIX = "account.mode.";

    public static final String ONLINE = AccountMode.PREFIX + "online";
    public static final String OFFLINE = AccountMode.PREFIX + "offline";
    public static final String HIDDEN = AccountMode.PREFIX + "hidden";
    public static final String SLEEP = AccountMode.PREFIX + "sleep";
}
