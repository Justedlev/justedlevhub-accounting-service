package com.justedlev.account.common.jaudit;

public interface JAuditValueConverter<I, O> {
    O convert(I in);

    class None implements JAuditValueConverter<Object, Object> {
        @Override
        public Object convert(Object in) {
            return null;
        }
    }
}
