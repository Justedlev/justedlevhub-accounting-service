package com.justedlev.account.common.jaudit;

import com.justedlev.account.component.JAuditManager;
import com.justedlev.account.repository.entity.JAudit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Slf4j
@Configurable
public class JAuditListener {
    @Autowired
    private JAuditManager jAuditManager;

    @PreRemove
    private void beforeRemove(Object entity) {
        jAuditManager.save(entity, JAudit.Operation.DELETED);
    }

    @PreUpdate
    private void beforeUpdate(Object entity) {
        jAuditManager.save(entity, JAudit.Operation.UPDATED);
    }
//        var fields = ReflectionUtils.getFields(
//                entity.getClass(), field -> field.isAnnotationPresent(JAuditInfo.class));
//
//        fields.forEach(field -> {
//            field.setAccessible(true);
//            var annotation = field.getAnnotation(JAuditInfo.class);
//            Object value;
//            Object oldValue;
//            try {
//                var converter = context.getBean(
//                        JAuditValueConverter.class, annotation.valueConverter());
//                value = converter.convert(field.get(entity));
//                oldValue = converter.convert(field.get(entity));
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//            log.info("Field {} was changed from {} to {}", field.getName(), oldValue, value);
//        });
//    }
}
