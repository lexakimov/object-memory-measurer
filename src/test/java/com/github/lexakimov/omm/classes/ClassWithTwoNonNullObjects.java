package com.github.lexakimov.omm.classes;

/**
 * 24 + 4128 + 16520 = 20672 bytes
 *
 * @author akimov
 * created at: 08.01.2023 23:45
 */
@SuppressWarnings("ALL")
public class ClassWithTwoNonNullObjects {
    public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithTwoNonNullObjects";

    static {
        assert FQN.equals(ClassWithTwoNonNullObjects.class.getName());
    }

    private final NonNullObject1 obj1 = new NonNullObject1();
    private final NonNullObject2 obj2 = new NonNullObject2();

    /**
     * 16 + 4112 = 4128 bytes
     */
    public static class NonNullObject1 {
        public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithTwoNonNullObjects$NonNullObject1";

        static {
            assert FQN.equals(NonNullObject1.class.getName());
        }

        private final int[] obj1_1 = new int[1024];
    }

    /**
     * 24 + 8248 * 2 = 16520 bytes
     */
    public static class NonNullObject2 {
        public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithTwoNonNullObjects$NonNullObject2";

        static {
            assert FQN.equals(NonNullObject2.class.getName());
        }

        NonNullObject3 obj2_1 = new NonNullObject3();
        NonNullObject3 obj2_2 = new NonNullObject3();
    }

    /**
     * 24 + 4112 * 2 = 8248 bytes
     */
    public static class NonNullObject3 {
        public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithTwoNonNullObjects$NonNullObject3";

        static {
            assert FQN.equals(NonNullObject3.class.getName());
        }

        private final int[] obj2__1 = new int[1024];
        private final int[] obj2__2 = new int[1024];
    }
}
