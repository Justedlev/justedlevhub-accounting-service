package com.justedlev.hub.component;

import com.justedlev.hub.util.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {
    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(Constants.BOT_ID.toString());
    }
}
