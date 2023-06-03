package com.justedlev.account.repository.entity;

import com.justedlev.account.audit.AuditColumn;
import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.Gender;
import com.justedlev.account.enumeration.ModeType;
import com.justedlev.account.util.DateTimeUtils;
import com.justedlev.account.util.Generator;
import com.justedlev.common.entity.Auditable;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@DynamicUpdate
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "account")
public class Account extends Auditable implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "account_id", updatable = false)
    private UUID id;
    @AuditColumn
    @Column(name = "nick_name", nullable = false)
    private String nickname;
    @AuditColumn
    @Column(name = "first_name")
    private String firstName;
    @AuditColumn
    @Column(name = "last_name")
    private String lastName;
    @AuditColumn
    @Column(name = "birth_date")
    private Timestamp birthDate;
    @AuditColumn
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @AuditColumn(hide = true)
    @Type(type = "jsonb")
    @Column(name = "avatar", columnDefinition = "jsonb")
    private Avatar avatar;
    @AuditColumn(hide = true)
    @Builder.Default
    @Column(
            name = "activation_code",
            length = 32,
            nullable = false,
            unique = true,
            updatable = false
    )
    private String activationCode = Generator.generateActivationCode();
    @AuditColumn
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private AccountStatusCode status = AccountStatusCode.UNCONFIRMED;
    @Getter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private ModeType mode = ModeType.OFFLINE;
    @Getter
    @Builder.Default
    @Column(name = "mode_at", nullable = false)
    private Timestamp modeAt = DateTimeUtils.nowTimestamp();
    @Version
    @Column(name = "version")
    private Long version;

    public void setMode(ModeType mode) {
        this.mode = mode;
        this.setModeAt(DateTimeUtils.nowTimestamp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
