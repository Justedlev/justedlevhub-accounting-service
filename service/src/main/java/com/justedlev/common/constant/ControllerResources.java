package com.justedlev.common.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerResources {
    public static final String API = "/api";
    public static final String V1 = "/v1";
    public static final String API_V1 = API + V1;
    public static final String PAGE = "/page";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String HISTORY = "/history";
    public static final String DEACTIVATE = "/deactivate";
    public static final String ACTIVATE = "/activate";
    public static final String CONFIRM = "/confirm";
    public static final String AVATAR = "/avatar";
    public static final String NICKNAME_VAR = "{nickname}";
    public static final String CODE_VAR = "{code}";
    public static final String EMAIL_VAR = "{email}";
    public static final String ID_VAR = "{id}";

    @UtilityClass
    public static class Account {
        public static final String ACCOUNTS = V1 + "/accounts";
        public static final String ACCOUNTS_HISTORY = ACCOUNTS + HISTORY;
        public static final String UPDATE_MODE = "/update-mode";
        public static final String NICKNAME = "/" + NICKNAME_VAR;
        public static final String ID = "/" + ID_VAR;
        public static final String EMAIL = "/" + EMAIL_VAR;
        public static final String NICKNAME_UPDATE = NICKNAME + UPDATE;
        public static final String NICKNAME_AVATAR = NICKNAME + AVATAR;
        public static final String ID_AVATAR = ID + AVATAR;
        public static final String NICKNAME_DEACTIVATE = NICKNAME + DEACTIVATE;
        public static final String NICKNAME_ACTIVATE = NICKNAME + ACTIVATE;
        public static final String V1_ACCOUNT_NICKNAME_ACTIVATE = NICKNAME + NICKNAME_ACTIVATE;
        public static final String NICKNAME_DELETE = NICKNAME + DELETE;
        public static final String CODE = "/" + CODE_VAR;
        public static final String CONFIRM_CODE = CONFIRM + CODE;
    }
}
