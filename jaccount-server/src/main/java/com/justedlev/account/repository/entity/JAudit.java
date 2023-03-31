package com.justedlev.account.repository.entity;

import com.justedlev.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "j_audit")
public class JAudit extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "entity_type")
    private Class<?> entityType;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "payload")
    private String payload;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "operation")
    @Enumerated(EnumType.STRING)
    private Operation operation;

    public enum Operation {
        UPDATED, CREATED, DELETED
    }
}
