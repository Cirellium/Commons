package net.cirellium.commons.common.util;

import java.util.Objects;

public class ExceptionUtils {
    
    public static Throwable findOriginalCause(Throwable throwable) {
        Objects.requireNonNull(throwable);

        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

}