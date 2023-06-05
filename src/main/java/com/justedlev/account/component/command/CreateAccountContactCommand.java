package com.justedlev.account.component.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountContactCommand {
    private UUID accountId;
    private UUID contactId;
    private boolean main;
}
