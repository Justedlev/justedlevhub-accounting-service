package com.justedlev.account.repository.entity.embedabble;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AccountContactId implements Serializable {
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "contact_id")
    private UUID contactId;
}
