package com.github.lexakimov.omm.classes;

/**
 * @author akimov
 * created at: 15.01.2023 18:02
 */
public class ClassWithCyclicLoop {

    public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithCyclicLoop";

    static {
        assert FQN.equals(ClassWithCyclicLoop.class.getName());
    }

    final Boolean a = true;
    final int b = 123;
    final InnerClass inner;

    public ClassWithCyclicLoop() {
        inner = new InnerClass(this);
    }

    public static class InnerClass {

        public static final String FQN = "com.github.lexakimov.omm.classes.ClassWithCyclicLoop$InnerClass";

        static {
            assert FQN.equals(InnerClass.class.getName());
        }

        final Long a = 123L;
        final ClassWithCyclicLoop parent;

        public InnerClass(ClassWithCyclicLoop parent) {
            this.parent = parent;
        }
    }

}
