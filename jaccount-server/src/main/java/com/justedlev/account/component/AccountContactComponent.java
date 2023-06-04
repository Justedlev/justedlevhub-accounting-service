package com.justedlev.account.component;

import com.justedlev.account.component.command.CreateAccountContactCommand;
import com.justedlev.account.repository.entity.AccountContact;

public interface AccountContactComponent {
    AccountContact create(CreateAccountContactCommand command);
}
