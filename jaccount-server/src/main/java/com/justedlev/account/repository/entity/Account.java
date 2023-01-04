package com.justedlev.account.repository.entity;

import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.Gender;
import com.justedlev.account.enumeration.ModeType;
import com.justedlev.account.model.Avatar;
import com.justedlev.account.model.Mode;
import com.justedlev.account.model.PhoneNumberInfo;
import com.justedlev.account.repository.entity.base.BaseEntity;
import com.justedlev.account.util.DateTimeUtils;
import com.justedlev.account.util.Generator;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
@Table(name = "accounts")
public class Account extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "account_id")
    private UUID id;
    @Column(name = "nick_name", nullable = false)
    private String nickname;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private Timestamp birthDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Email
    @Column(name = "email", nullable = false)
    private String email;
    @Type(type = "jsonb")
    @Column(name = "phone_number_info", columnDefinition = "jsonb")
    private PhoneNumberInfo phoneNumberInfo;
    @Type(type = "jsonb")
    @Column(name = "avatar", columnDefinition = "jsonb")
    private Avatar avatar;
    @Builder.Default
    @Column(name = "activation_code", length = 32, nullable = false, unique = true)
    private String activationCode = Generator.generateActivationCode();
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private AccountStatusCode status = AccountStatusCode.UNCONFIRMED;
    @Builder.Default
    @Type(type = "jsonb")
    @Column(name = "mode", columnDefinition = "jsonb", nullable = false)
    private Mode mode = new Mode();

    public void setMode(ModeType mode) {
        this.mode.setModeType(mode);
        this.mode.setModeAt(DateTimeUtils.nowTimestamp());
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
