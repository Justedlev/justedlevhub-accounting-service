package com.justedlev.account.repository.entity;

import com.justedlev.common.entity.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicUpdate
@Table(name = "contact")
public class Contact extends Auditable implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "contact_id")
    private UUID id;
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
        return getId() != null && Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
