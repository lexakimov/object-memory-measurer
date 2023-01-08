package com.github.lexakimov.omm;

import java.lang.instrument.Instrumentation;
import static com.github.lexakimov.omm.util.Logger.info;
import static java.lang.String.format;

/**
 * @author akimov
 * created at: 06.01.2023 13:10
 */
public class MemoryMeasureUtil {

    private static final Instrumentation instrumentation = InstrumentationAgent.instrumentation();

    public static final String PRINT_WITH_CLASS_NAME_PATTERN = "class: %30s size: %5s";

    private MemoryMeasureUtil() {
    }

    public static long getSizeInBytes(Object object) {
        return instrumentation.getObjectSize(object);
    }

    public static void printSizeInBytes(Object object) {
        info(getSizeInBytes(object));
    }

    public static void printWithClassName(boolean object) {
        info(format(PRINT_WITH_CLASS_NAME_PATTERN, boolean.class.getName(), instrumentation.getObjectSize(object)));
    }

    public static void printWithClassName(byte object) {
        info(format(PRINT_WITH_CLASS_NAME_PATTERN, byte.class.getName(), instrumentation.getObjectSize(object)));
    }

    public static void printWithClassName(char object) {
        info(format(PRINT_WITH_CLASS_NAME_PATTERN, char.class.getName(), instrumentation.getObjectSize(object)));
    }

    public static void printWithClassName(short object) {
        info(format(PRINT_WITH_CLASS_NAME_PATTERN, short.class.getName(), instrumentation.getObjectSize(object)));
    }

    public static void printWithClassName(int object) {
        info(format(PRINT_WITH_CLASS_NAME_PATTERN, int.class.getName(), instrumentation.getObjectSize(object)));
    }

    public static void printWithClassName(long object) {
        info(format(PRINT_WITH_CLASS_NAME_PATTERN, long.class.getName(), instrumentation.getObjectSize(object)));
    }

    public static void printWithClassName(float object) {
        info(format(PRINT_WITH_CLASS_NAME_PATTERN, float.class.getName(), instrumentation.getObjectSize(object)));
    }

    public static void printWithClassName(double object) {
        info(format(PRINT_WITH_CLASS_NAME_PATTERN, double.class.getName(), instrumentation.getObjectSize(object)));
    }

}
