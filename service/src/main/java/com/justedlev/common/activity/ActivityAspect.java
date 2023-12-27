package com.justedlev.common.activity;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ActivityAspect {
    private final ActivityRegistrarRepository<?> activityRegistrarRepository;
}
