package com.github.lexakimov.omm.classes;

/**
 * @author akimov
 * created at: 08.01.2023 10:04
 */
@SuppressWarnings("ConstantValue")
public class ClassWithTwoIntFieldAndOneLong {
    public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneLong";

    static {
        assert FQN.equals(ClassWithTwoIntFieldAndOneLong.class.getName());
    }

    int a = 100;
    int b = 100;
    long c = 100;
}
