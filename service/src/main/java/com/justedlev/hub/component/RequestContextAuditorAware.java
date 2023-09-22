package com.justedlev.hub.component;

import com.justedlev.hub.util.Constants;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@Component
public class RequestContextAuditorAware implements AuditorAware<String> {
    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(RequestContextHolder.currentRequestAttributes())
                .map(requestAttributes -> requestAttributes.getAttribute("X-user-id", SCOPE_REQUEST))
                .map(Object::toString)
                .or(() -> Optional.of(Constants.BOT_ID.toString()));
    }
}
