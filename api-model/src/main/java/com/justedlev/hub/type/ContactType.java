package com.justedlev.hub.type;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ContactType implements Comparable<ContactType>, Serializable {
    public static final String PREFIX = "contact.type.";

    private static final String EMAIL_LABEL = PREFIX + "email";
    public static final ContactType EMAIL = new ContactType(EMAIL_LABEL);

    private static final String PHONE_NUMBER_LABEL = PREFIX + "phone-number";
    public static final ContactType PHONE_NUMBER = new ContactType(PHONE_NUMBER_LABEL);

    private static final Set<ContactType> values = Set.of(EMAIL, PHONE_NUMBER);

    private final String label;

    @NonNull
    public static ContactType valueOf(@NonNull String label) {
        return switch (label) {
            case EMAIL_LABEL -> EMAIL;
            case PHONE_NUMBER_LABEL -> PHONE_NUMBER;
            default -> new ContactType(label);
        };
    }

    @Override
    public int compareTo(ContactType other) {
        return this.label.compareTo(other.label);
    }
}
