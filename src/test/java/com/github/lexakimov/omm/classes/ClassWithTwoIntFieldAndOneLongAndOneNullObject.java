package com.github.lexakimov.omm.classes;

/**
 * @author akimov
 * created at: 08.01.2023 10:04
 */
public class ClassWithTwoIntFieldAndOneLongAndOneNullObject {
    public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneLongAndOneNullObject";

    static {
        assert FQN.equals(ClassWithTwoIntFieldAndOneLongAndOneNullObject.class.getName());
    }

    int a = 100;
    int b = 100;
    long c = 100;
    Object d = null;
}
