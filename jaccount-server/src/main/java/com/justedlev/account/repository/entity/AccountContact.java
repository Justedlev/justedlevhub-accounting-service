package com.justedlev.account.repository.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "account_contact")
@Embeddable
public class AccountContact {
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "contact_id")
    private UUID contactId;
}
