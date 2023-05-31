package com.justedlev.account.repository.entity;

import com.justedlev.account.common.jaudit.JAuditable;
import com.justedlev.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@JAuditable
@Table(name = "contact")
public class Contact extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "contact_id")
    private UUID id;
    @Builder.Default
    @Column(name = "main", nullable = false)
    private boolean main = Boolean.FALSE;
    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Version
    @Column(name = "version")
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Contact contact = (Contact) o;
        return id != null && Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
