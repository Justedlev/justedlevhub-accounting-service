package com.justedlev.account.repository.entity;

import com.justedlev.common.entity.Imprint;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "account_journal")
public class AccountJournal extends Imprint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
