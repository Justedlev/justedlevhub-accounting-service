package com.justedlev.account.client;

public final class EndpointConstant {
    public static final String V1 = "/v1";
    public static final String PAGE = "/page";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String DEACTIVATE = "/deactivate";
    public static final String ACTIVATE = "/activate";
    public static final String CONFIRM = "/confirm";
    public static final String AVATAR = "/avatar";
    public static final String UPDATE_MODE = "/update-mode";
    public static final String ACCOUNT = "/account";
    public static final String HISTORY = "/history";

    // Account
    public static final String V1_ACCOUNT = V1 + ACCOUNT;
    public static final String V1_ACCOUNT_CREATE = V1_ACCOUNT + CREATE;
    public static final String V1_ACCOUNT_PAGE = V1_ACCOUNT + PAGE;
    public static final String V1_ACCOUNT_UPDATE_MODE = V1_ACCOUNT + UPDATE_MODE;
    public static final String NICKNAME = "/" + PathVariableConstant.NICKNAME;
    public static final String EMAIL = "/" + PathVariableConstant.EMAIL;
    public static final String V1_ACCOUNT_NICKNAME = V1_ACCOUNT + NICKNAME;
    public static final String NICKNAME_UPDATE = NICKNAME + UPDATE;
    public static final String V1_ACCOUNT_NICKNAME_UPDATE = V1_ACCOUNT + NICKNAME_UPDATE;
    public static final String NICKNAME_UPDATE_AVATAR = NICKNAME_UPDATE + AVATAR;
    public static final String V1_ACCOUNT_NICKNAME_UPDATE_AVATAR = V1_ACCOUNT + NICKNAME_UPDATE_AVATAR;
    public static final String NICKNAME_DEACTIVATE = NICKNAME + DEACTIVATE;
    public static final String V1_ACCOUNT_NICKNAME_DEACTIVATE = V1_ACCOUNT + NICKNAME_DEACTIVATE;
    public static final String NICKNAME_ACTIVATE = NICKNAME + ACTIVATE;
    public static final String V1_ACCOUNT_NICKNAME_ACTIVATE = NICKNAME + NICKNAME_ACTIVATE;
    public static final String NICKNAME_DELETE = NICKNAME + DELETE;
    public static final String V1_ACCOUNT_NICKNAME_DELETE = V1_ACCOUNT + NICKNAME_DELETE;
    public static final String V1_ACCOUNT_CONFIRM = V1_ACCOUNT + CONFIRM;
    public static final String CODE = "/" + PathVariableConstant.CODE;
    public static final String CONFIRM_CODE = CONFIRM + CODE;
    public static final String V1_ACCOUNT_CONFIRM_CODE = V1_ACCOUNT_CONFIRM + CODE;

    // History
    public static final String V1_HISTORY = V1 + HISTORY;
    public static final String V1_HISTORY_ACCOUNT = V1_HISTORY + ACCOUNT;

    private EndpointConstant() {
        throw new IllegalStateException("Constants class");
    }
}
