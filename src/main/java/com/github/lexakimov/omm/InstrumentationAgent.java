package com.github.lexakimov.omm;

import com.github.lexakimov.omm.util.Logger;
import java.lang.instrument.Instrumentation;

/**
 * @author akimov
 * created at: 06.01.2023 12:38
 */
public final class InstrumentationAgent {

    private static volatile Instrumentation globalInstrumentation;

    public static final String ERROR_MSG = "Instrumentation is not setup properly. " +
                                           "You have to pass VM option '-javaagent:path/to/object-memory-measurer.jar'";

    public static void premain(final String agentArgs, final Instrumentation inst) {
        if (globalInstrumentation != null) {
            throw new AssertionError("Agent already initialized");
        }

        globalInstrumentation = inst;
    }

    public static Instrumentation instrumentation() {
        if (globalInstrumentation == null) {
            Logger.error(ERROR_MSG);
            throw new IllegalStateException(ERROR_MSG);
        }
        return globalInstrumentation;
    }
}
