package com.justedlev.account.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountContactEntity {
    private boolean main;
    private Account account;
    private Contact contact;
}
