package com.justedlevhub.account.component.impl;

import com.justedlevhub.account.component.AccountContactComponent;
import com.justedlevhub.account.component.command.CreateAccountContactCommand;
import com.justedlevhub.account.repository.AccountContactRepository;
import com.justedlevhub.account.repository.entity.AccountContact;
import com.justedlevhub.account.repository.entity.embedabble.AccountContactId;
import com.justedlevhub.account.repository.specification.AccountContactSpecification;
import com.justedlevhub.account.repository.specification.filter.AccountContactFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;

@Component
@RequiredArgsConstructor
public class AccountContactComponentImpl implements AccountContactComponent {
    private final AccountContactRepository accountContactRepository;
    private final ModelMapper mapper;

    @Override
    public AccountContact create(CreateAccountContactCommand command) {
        var id = mapper.map(command, AccountContactId.class);
        var filter = AccountContactFilter.builder()
                .id(id)
                .build();

        if (accountContactRepository.exists(AccountContactSpecification.from(filter)) && command.isMain()) {
            throw new EntityExistsException("Already exist");
        }

        var entity = AccountContact.builder()
                .id(id)
                .main(command.isMain())
                .build();

        return accountContactRepository.save(entity);
    }
}
