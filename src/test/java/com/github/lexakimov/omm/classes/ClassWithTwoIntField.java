package com.github.lexakimov.omm.classes;

/**
 * @author akimov
 * created at: 08.01.2023 10:04
 */
public class ClassWithTwoIntField {
    public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithTwoIntField";

    static {
        assert FQN.equals(ClassWithTwoIntField.class.getName());
    }

    int a = 100;
    int b = 100;
}
