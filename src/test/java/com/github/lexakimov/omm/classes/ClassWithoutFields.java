package com.github.lexakimov.omm.classes;

/**
 * @author akimov
 * created at: 08.01.2023 10:04
 */
public class ClassWithoutFields {

    public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithoutFields";

    static {
        assert FQN.equals(ClassWithoutFields.class.getName());
    }

}
