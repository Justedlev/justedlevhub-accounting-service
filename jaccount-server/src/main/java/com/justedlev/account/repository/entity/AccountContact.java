package com.justedlev.account.repository.entity;


import com.justedlev.account.repository.entity.embedabble.AccountContactId;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "account_contact")
public class AccountContact {
    @EmbeddedId
    private AccountContactId id;
    @Builder.Default
    @Column(name = "main", nullable = false)
    private boolean main = Boolean.FALSE;
    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @MapsId("contactId")
    @JoinColumn(name = "contact_id")
    private Contact contact;
}
