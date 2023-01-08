package com.github.lexakimov.omm.classes;

/**
 * @author akimov
 * created at: 08.01.2023 10:04
 */
public class EmptyClass {

    public static final String FQN = "com.github.lexakimov.omm.classes.EmptyClass";

    static {
        assert FQN.equals(EmptyClass.class.getName());
    }

}
