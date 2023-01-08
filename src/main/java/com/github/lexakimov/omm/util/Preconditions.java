package com.github.lexakimov.omm.util;

/**
 * @author akimov
 * created at: 06.01.2023 12:53
 */
class Preconditions {

    static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

}
