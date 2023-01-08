package com.github.lexakimov.omm.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akimov
 * created at: 08.01.2023 19:07
 */
@SuppressWarnings({"FieldMayBeFinal", "unused", "ConstantValue"})
public class NonAbstractClass2 extends NonAbstractClass1 {
    public static final String FQN = "com.github.lexakimov.omm.classes.NonAbstractClass2";

    static {
        assert FQN.equals(NonAbstractClass2.class.getName());
    }

    private int e = 1000;
    private List<String> f = new ArrayList<>();
}
