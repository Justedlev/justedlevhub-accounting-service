package com.justedlev.account.common.jaudit;

public interface JAuditValueConverter<I> {
    String convert(I in);

    class None implements JAuditValueConverter<Object> {
        @Override
        public String convert(Object in) {
            return "";
        }
    }
}
