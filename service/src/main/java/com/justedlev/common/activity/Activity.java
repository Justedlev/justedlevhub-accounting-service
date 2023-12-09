//package com.justedlev.hub.common.activity;
//
//import com.justedlev.hub.repository.entity.Auditable;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.envers.AuditOverride;
//import org.hibernate.envers.Audited;
//
//import java.io.Serializable;
//
//@Audited
//@AuditOverride(forClass = Auditable.class)
//@SuperBuilder
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Getter
//@Setter
//@Entity
//@DynamicUpdate
//@Table(name = "activity")
//public class Activity<R extends Serializable> extends Auditable<Long> implements Serializable {
//    @Column(name = "reference_id")
//    private Object referenceId;
//
//    @Column(name = "entity_type")
//    private String entityType;
//
//    @Column(name = "value_type")
//    private String valueType;
//
//    @Column(name = "current_value")
//    private Object currentValue;
//
//    @Column(name = "previous_value")
//    private Object previousValue;
//}
