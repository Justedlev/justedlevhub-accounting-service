package com.justedlevhub.account.component;

import com.justedlevhub.account.component.command.CreateAccountContactCommand;
import com.justedlevhub.account.repository.entity.AccountContact;

public interface AccountContactComponent {
    AccountContact create(CreateAccountContactCommand command);
}
