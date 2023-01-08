package com.github.lexakimov.omm.classes;

/**
 * @author akimov
 * created at: 08.01.2023 19:06
 */
@SuppressWarnings({"FieldMayBeFinal", "unused", "ConstantValue"})
public abstract class AbstractClass {
    public static final String FQN = "com.github.lexakimov.omm.classes.AbstractClass";

    static {
        assert FQN.equals(AbstractClass.class.getName());
    }

    private int a = 1000;
    private Object b = null;
    private String c = "test";
}
