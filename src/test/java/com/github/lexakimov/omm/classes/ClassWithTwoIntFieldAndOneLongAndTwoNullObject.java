package com.github.lexakimov.omm.classes;

/**
 * @author akimov
 * created at: 08.01.2023 10:04
 */
public class ClassWithTwoIntFieldAndOneLongAndTwoNullObject {

    public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneLongAndTwoNullObject";

    static {
        assert FQN.equals(ClassWithTwoIntFieldAndOneLongAndTwoNullObject.class.getName());
    }

    int a = 100;
    int b = 100;
    long c = 100;
    Object d = null;
    Object e = null;
}
