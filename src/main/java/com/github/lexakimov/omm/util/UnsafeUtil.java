package com.github.lexakimov.omm.util;

import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Copied from <a href="https://github.com/openjdk/jol/blob/5dde423fe3b9ed2648104c538eaf25d8086393e4/jol-core/src/main/java/org/openjdk/jol/vm/HotspotUnsafe.java">jol-core</a>
 */
public class UnsafeUtil {

    static final Unsafe UNSAFE;

    static {
        UNSAFE = AccessController.doPrivileged((PrivilegedAction<Unsafe>) () -> {
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                return (Unsafe) theUnsafe.get(null);
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        });
    }

    private static int arrayObjectBase = UNSAFE.arrayBaseOffset(Object[].class);

    private static final ThreadLocal<Object[]> BUFFERS = ThreadLocal.withInitial(() -> new Object[1]);

    public static long addressOf(Object o) {
        Object[] array = BUFFERS.get();
        array[0] = o;
        long objectAddress;
        int oopSize = guessOopSize();
        switch (oopSize) {
            case 4:
                objectAddress = UNSAFE.getInt(array, arrayObjectBase) & 0xFFFFFFFFL;
                break;
            case 8:
                objectAddress = UNSAFE.getLong(array, arrayObjectBase);
                break;
            default:
                throw new Error("unsupported address size: " + oopSize);
        }

        array[0] = null;

        return objectAddress;
    }

    private static int guessOopSize() {
        // When running with CompressedOops on 64-bit platform, the address size
        // reported by Unsafe is still 8, while the real reference fields are 4 bytes long.
        // Try to guess the reference field size with this naive trick.
        int oopSize;
        try {
            long off1 = UNSAFE.objectFieldOffset(CompressedOopsClass.class.getField("obj1"));
            long off2 = UNSAFE.objectFieldOffset(CompressedOopsClass.class.getField("obj2"));
            oopSize = (int) Math.abs(off2 - off1);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Infrastructure failure", e);
        }

        return oopSize;
    }

    static class CompressedOopsClass {
        public Object obj1;
        public Object obj2;
    }
}
