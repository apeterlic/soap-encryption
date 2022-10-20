package dev.beenary.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

@Slf4j
public class Preconditions {

    /**
     * This class should not be instantiated. Provides only static methods.
     */
    private Preconditions() {
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            log.error("ERROR {}", errorMessage);
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return reference;
    }

}
