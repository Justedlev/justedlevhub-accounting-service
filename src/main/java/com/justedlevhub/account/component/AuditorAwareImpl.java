package com.justedlevhub.account.component;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    private static final UUID BOT = UUID.nameUUIDFromBytes("Justedlev BOT".getBytes(StandardCharsets.UTF_8));

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(BOT.toString());
    }
}
