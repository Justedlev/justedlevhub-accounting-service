package com.justedlev.account.util;

import org.reflections.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

public class AuditUtils {
    @SuppressWarnings("unchecked")
    public static Set<Field> findAnnotatedFields(Object target, Class<? extends Annotation> annotation) {
        return ReflectionUtils.getFields(target.getClass(), field -> field.isAnnotationPresent(annotation));
    }

    @SuppressWarnings("unchecked")
    public static Optional<Method> findGetMethod(Object target, Field field) {
        return ReflectionUtils.getMethods(
                        target.getClass(),
                        method -> method.getName().equalsIgnoreCase("get" + field.getName())
                )
                .stream()
                .findFirst();
    }

    public static Optional<Object> getFieldValue(Object target, Field field) {
        return findGetMethod(target, field)
                .map(method -> {
                    try {
                        return method.invoke(target);
                    } catch (Exception e) {
                        return null;
                    }
                });
    }
}
