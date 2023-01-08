package com.github.lexakimov.omm.classes;

/**
 * @author akimov
 * created at: 08.01.2023 10:04
 */
@SuppressWarnings("ConstantValue")
public class ClassWithOneIntField {
    public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithOneIntField";

    static {
        assert FQN.equals(ClassWithOneIntField.class.getName());
    }

    int a = 100;
}
