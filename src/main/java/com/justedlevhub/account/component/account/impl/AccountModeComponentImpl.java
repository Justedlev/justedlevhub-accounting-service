package com.justedlevhub.account.component.account.impl;

import com.justedlevhub.account.component.account.AccountModeComponent;
import com.justedlevhub.account.properties.JAccountProperties;
import com.justedlevhub.account.repository.AccountRepository;
import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.account.repository.specification.AccountSpecification;
import com.justedlevhub.account.repository.specification.filter.AccountFilter;
import com.justedlevhub.api.model.request.UpdateAccountModeRequest;
import com.justedlevhub.api.model.response.AccountResponse;
import com.justedlevhub.api.type.ModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountModeComponentImpl implements AccountModeComponent {
    private final JAccountProperties properties;
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    @Override
    public List<AccountResponse> updateMode(UpdateAccountModeRequest request) {
        var activeAccounts = getActiveAccount(request);
        activeAccounts.forEach(current -> current.setMode(request.getToMode()));
        var res = accountRepository.saveAll(activeAccounts);
        log.info("Changed {} inactive accounts to mode : {}", activeAccounts.size(), request.getToMode());

        return res.parallelStream()
                .map(account -> mapper.map(account, AccountResponse.class))
                .toList();
    }

    private List<Account> getActiveAccount(UpdateAccountModeRequest request) {
        var filter = AccountFilter.builder()
                .modes(request.getFromModes())
                .build();
        var now = System.currentTimeMillis();
        var duration = getDuration(request.getToMode());

        return accountRepository.findAll(AccountSpecification.from(filter))
                .parallelStream()
                .filter(current -> filterOutByModeAt(current, now, duration))
                .toList();
    }

    private Duration getDuration(ModeType modeType) {
        return switch (modeType) {
            case SLEEP -> properties.getActivityTime();
            case OFFLINE -> properties.getOfflineAfterTime();
            default -> Duration.of(1, ChronoUnit.MINUTES);
        };
    }

    private boolean filterOutByModeAt(Account account, long now, Duration duration) {
        return Optional.ofNullable(account.getModeAt())
                .map(Date::getTime)
                .map(current -> now - current)
                .map(current -> current >= duration.toMillis())
                .orElse(Boolean.FALSE);
    }
}
