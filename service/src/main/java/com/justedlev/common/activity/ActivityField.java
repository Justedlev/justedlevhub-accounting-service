package com.justedlev.common.activity;

import java.lang.annotation.*;

@Target({ElementType.FIELD,})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActivityField {
}
