package com.github.lexakimov.omm.util;

/**
 * @author akimov
 * created at: 08.01.2023 14:49
 */
@FunctionalInterface
public interface TriFunction<A1, A2, A3, R> {
    R apply(A1 a1, A2 a2, A3 a3);
}
