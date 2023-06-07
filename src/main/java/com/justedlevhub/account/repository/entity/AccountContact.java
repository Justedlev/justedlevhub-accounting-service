package com.justedlevhub.account.repository.entity;


import com.justedlevhub.account.repository.entity.embedabble.AccountContactId;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "account_contact")
public class AccountContact implements Serializable {
    @EmbeddedId
    private AccountContactId id;
    @Builder.Default
    @Column(name = "main", nullable = false)
    private boolean main = Boolean.FALSE;
}
