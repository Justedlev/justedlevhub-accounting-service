package com.justedlev.account.boot;

import com.justedlev.account.component.AccountComponent;
import com.justedlev.account.component.ContactComponent;
import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.Gender;
import com.justedlev.account.enumeration.ModeType;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.properties.JAccountProperties;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Contact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.modelmapper.TypeMap;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@Order(value = 10)
@RequiredArgsConstructor
public class RandomAccountsBoot implements ApplicationRunner {
    private static final Boolean FILL = Boolean.TRUE;
    private final AccountComponent accountComponent;
    private final ContactComponent contactComponent;
    private final JAccountProperties accountProperties;
    private final TypeMap<Account, AccountResponse> typeMap;

    @Override
    public void run(ApplicationArguments args) {
        if (Boolean.TRUE.equals(FILL)) {
            var accountStatuses = AccountStatusCode.values();
            var modes = ModeType.values();
            var genders = Gender.values();
            var phonePrefix = "+972";
            var emailPostfix = "@" + accountProperties.getService().getName().toLowerCase() + ".co.il";

            List<Account> list = new ArrayList<>();
            for (int i = 0; i < RandomUtils.nextInt(50, 100); i++) {
                var phoneNumber = phonePrefix + RandomUtils.nextInt(100000000, 999999999);
                var nickname = RandomStringUtils.randomAlphanumeric(4, 8);
                var contact = Contact.builder()
                        .phoneNumber(phoneNumber)
                        .email(nickname + emailPostfix)
                        .build();

                var account = Account.builder()
                        .nickname(nickname)
                        .status(accountStatuses[getRandomIndex(accountStatuses.length)])
                        .mode(modes[getRandomIndex(modes.length)])
                        .gender(genders[getRandomIndex(genders.length)])
                        .firstName(RandomStringUtils.randomAlphanumeric(4, 8))
                        .lastName(RandomStringUtils.randomAlphanumeric(4, 8))
                        .createdAt(new Timestamp(RandomUtils.nextLong(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3), System.currentTimeMillis())))
                        .build();
                list.add(account);
            }

            var res = accountComponent.saveAll(list);
//            log.info("Created {} random accounts", res.size());
        }
    }

    private Set<Contact> randomContacts(String phonePrefix, String emailPostfix) {
        return IntStream.range(0, RandomUtils.nextInt(1, 5))
                .mapToObj(i -> Contact.builder()
                        .phoneNumber(phonePrefix + RandomUtils.nextInt(100000000, 999999999))
                        .email(RandomStringUtils.randomAlphanumeric(4, 8) + emailPostfix)
                        .build())
                .collect(Collectors.toSet());
    }

    private int getRandomIndex(int length) {
        return RandomUtils.nextInt(0, length);
    }
}
