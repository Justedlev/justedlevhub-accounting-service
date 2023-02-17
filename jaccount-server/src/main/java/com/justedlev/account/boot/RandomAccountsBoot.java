package com.justedlev.account.boot;

import com.justedlev.account.common.converter.PhoneNumberConverter;
import com.justedlev.account.component.AccountComponent;
import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.Gender;
import com.justedlev.account.enumeration.ModeType;
import com.justedlev.account.repository.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Order(value = 10)
@RequiredArgsConstructor
public class RandomAccountsBoot implements ApplicationRunner {
    private static final String SYMBOLS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
    private static final Boolean FILL = Boolean.FALSE;
    private final AccountComponent accountComponent;
    private final PhoneNumberConverter phoneNumberConverter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (Boolean.TRUE.equals(FILL)) {
            var accountStatuses = AccountStatusCode.values();
            var modes = ModeType.values();
            var genders = Gender.values();
            var count = RandomUtils.nextInt(10, 50);
            var countries = new int[]{1202, 97253};

            List<Account> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                var phone = phoneNumberConverter.convert("+" + countries[getRandomIndex(countries.length)] + RandomUtils.nextInt(1000000, 9999999));
                var username = getRandomName();
                var account = Account.builder()
                        .nickname(username)
                        .email(username + "@mail.co")
                        .status(accountStatuses[getRandomIndex(accountStatuses.length)])
                        .mode(modes[getRandomIndex(modes.length)])
                        .gender(genders[getRandomIndex(genders.length)])
                        .firstName(getRandomName())
                        .lastName(getRandomName())
                        .phoneNumberInfo(phone)
                        .build();
                list.add(account);
            }

            var res = accountComponent.saveAll(list);
            log.info("Created {} random accounts", res.size());
        }
    }

    private int getRandomIndex(int length) {
        return RandomUtils.nextInt(0, length);
    }

    private String getRandomName() {
        return RandomStringUtils.random(RandomUtils.nextInt(4, 9), SYMBOLS);
    }
}
