package com.github.lexakimov.omm.classes;

/**
 * 24 + 16 = 40 bytes
 *
 * @author akimov
 * created at: 08.01.2023 13:24
 */
@SuppressWarnings("ConstantValue")
public class ClassWithTwoIntFieldAndOneNonNullObject {
    public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneNonNullObject";

    static {
        assert FQN.equals(ClassWithTwoIntFieldAndOneNonNullObject.class.getName());
    }

    int a = 100;
    Object d = new SomeObject();

    /**
     * 16 bytes
     */
    public static class SomeObject {
        public static final String FQN =
                "com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneNonNullObject$SomeObject";

        static {
            assert FQN.equals(SomeObject.class.getName());
        }

        int a = 1000;
    }
}
