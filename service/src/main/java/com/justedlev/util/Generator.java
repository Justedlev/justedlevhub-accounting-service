package com.justedlev.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@UtilityClass
public final class Generator {
    private static final String SYMBOLS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890!@#$%^&?";

    public static Integer generateNumberCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Stream.generate(() -> randomInt(9))
                .limit(length)
                .forEach(stringBuilder::append);

        return Integer.parseInt(stringBuilder.toString());
    }

    public static int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    public static String generateSecretCode() {
        return generateConfirmCode();
    }

    public static String generateConfirmCode() {
        return RandomStringUtils.randomAlphanumeric(16, 32);
    }

    public static String generatePassword() {
        return RandomStringUtils.random(RandomUtils.nextInt(12, 32), SYMBOLS);
    }
}