package com.justedlev.hub.component.account.impl;

import com.justedlev.hub.component.account.AccountModeComponent;
import com.justedlev.hub.configuration.properties.ApplicationProperties;
import com.justedlev.hub.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.model.response.AccountResponse;
import com.justedlev.hub.repository.AccountRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.specification.AccountSpecification;
import com.justedlev.hub.type.AccountMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountModeComponentImpl implements AccountModeComponent {
    private final ApplicationProperties properties;
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    @Override
    public List<AccountResponse> updateMode(UpdateAccountModeRequest request) {
//        var activeAccounts = getActiveAccount(request);
//        activeAccounts.forEach(current -> current.setMode(request.getToMode()));
//        var res = accountRepository.saveAll(activeAccounts);
//        log.info("Changed {} inactive accounts to mode : {}", activeAccounts.size(), request.getToMode());
//
//        return res.parallelStream()
//                .map(account -> mapper.map(account, AccountResponse.class))
//                .toList();
        return List.of();
    }

    private List<Account> getActiveAccount(UpdateAccountModeRequest request) {
        var spec = AccountSpecification.builder()
//                .modes(request.getFromAccountModes())
                .build();
        var now = System.currentTimeMillis();
        var duration = getDuration(request.getToAccountMode());

        return accountRepository.findAll(spec)
                .parallelStream()
                .filter(current -> filterOutByModeAt(current, now, duration))
                .toList();
    }

    private Duration getDuration(String accountMode) {
        return switch (accountMode) {
            case AccountMode.SLEEP -> properties.getActivityTime();
            case AccountMode.OFFLINE -> properties.getOfflineAfterTime();
            default -> Duration.of(1, ChronoUnit.MINUTES);
        };
    }

    private boolean filterOutByModeAt(Account account, long now, Duration duration) {
//        return Optional.ofNullable(account.getModeAt())
//                .map(Date::getTime)
//                .map(current -> now - current)
//                .map(current -> current >= duration.toMillis())
//                .orElse(Boolean.FALSE);
        return false;
    }
}
