package com.github.lexakimov.omm.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akimov
 * created at: 08.01.2023 19:07
 */
@SuppressWarnings({"FieldMayBeFinal", "unused", "ConstantValue"})
public class NonAbstractClass1 extends AbstractClass {
    public static final String FQN = "com.github.lexakimov.omm.classes.NonAbstractClass1";

    static {
        assert FQN.equals(NonAbstractClass1.class.getName());
    }

    private int d = 1000;
    private List<String> e = new ArrayList<>();

}
