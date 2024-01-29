package com.justedlev.hub.type;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountStatus {
    private static final String PREFIX = "account.status.";

    public static final String ACTIVE = AccountStatus.PREFIX + "active";
    public static final String UNCONFIRMED = AccountStatus.PREFIX + "unconfirmed";
    public static final String RESTORED = AccountStatus.PREFIX + "restored";
    public static final String DEACTIVATED = AccountStatus.PREFIX + "deactivated";
    public static final String DELETED = AccountStatus.PREFIX + "deleted";
}
