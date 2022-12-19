package com.justedlev.account.component.impl;

import com.justedlev.account.component.PageCounterComponent;
import com.justedlev.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageCounterComponentImpl implements PageCounterComponent {
    private final AccountRepository accountRepository;

    @Override
    public int accountPageCount(int size) {
        return (int) Math.ceil((double) accountRepository.count() / size);
    }
}
