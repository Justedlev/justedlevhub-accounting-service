package com.justedlev.account.common.jaudit;

import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import javax.persistence.PreUpdate;

@Slf4j
@Configurable
public class JAuditListener {
    @Autowired
    private ApplicationContext context;

    @PreUpdate
    private void beforeUpdate(Object entity) {
        var fields = ReflectionUtils.getFields(
                entity.getClass(), field -> field.isAnnotationPresent(JAudit.class));

        fields.forEach(field -> {
            field.setAccessible(true);
            var annotation = field.getAnnotation(JAudit.class);
            Object value;
            Object oldValue;
            try {
                var converter = context.getBean(
                        JAuditValueConverter.class, annotation.valueConverter());
                value = converter.convert(field.get(entity));
                oldValue = converter.convert(field.get(entity));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            log.info("Field {} was changed from {} to {}", field.getName(), oldValue, value);
        });
    }
}
