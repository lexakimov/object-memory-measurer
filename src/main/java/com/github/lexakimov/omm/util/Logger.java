package com.github.lexakimov.omm.util;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * @author akimov
 * created at: 06.01.2023 12:59
 */
public class Logger {

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // High Intensity
    private static final String BRIGHT_BLACK = "\033[0;90m";  // BLACK
    private static final String BRIGHT_RED = "\033[0;91m";    // RED
    private static final String BRIGHT_GREEN = "\033[0;92m";  // GREEN
    private static final String BRIGHT_YELLOW = "\033[0;93m"; // YELLOW
    private static final String BRIGHT_BLUE = "\033[0;94m";   // BLUE
    private static final String BRIGHT_PURPLE = "\033[0;95m"; // PURPLE
    private static final String BRIGHT_CYAN = "\033[0;96m";   // CYAN
    private static final String BRIGHT_WHITE = "\033[0;97m";  // WHITE

    // Bold High Intensity
    private static final String BOLD_BRIGHT_BLACK = "\033[1;90m"; // BLACK
    private static final String BOLD_BRIGHT_RED = "\033[1;91m";   // RED
    private static final String BOLD_BRIGHT_GREEN = "\033[1;92m"; // GREEN
    private static final String BOLD_BRIGHT_YELLOW = "\033[1;93m";// YELLOW
    private static final String BOLD_BRIGHT_BLUE = "\033[1;94m";  // BLUE
    private static final String BOLD_BRIGHT_PURPLE = "\033[1;95m";// PURPLE
    private static final String BOLD_BRIGHT_CYAN = "\033[1;96m";  // CYAN
    private static final String BOLD_BRIGHT_WHITE = "\033[1;97m"; // WHITE

    // Background
    private static final String BACKGROUND_BLACK = "\u001B[40m";
    private static final String BACKGROUND_RED = "\u001B[41m";
    private static final String BACKGROUND_GREEN = "\u001B[42m";
    private static final String BACKGROUND_YELLOW = "\u001B[43m";
    private static final String BACKGROUND_BLUE = "\u001B[44m";
    private static final String BACKGROUND_PURPLE = "\u001B[45m";
    private static final String BACKGROUND_CYAN = "\u001B[46m";
    private static final String BACKGROUND_WHITE = "\u001B[47m";

    // High Intensity backgrounds
    private static final String BACKGROUND_BRIGHT_BLACK = "\033[0;100m";// BLACK
    private static final String BACKGROUND_BRIGHT_RED = "\033[0;101m";// RED
    private static final String BACKGROUND_BRIGHT_GREEN = "\033[0;102m";// GREEN
    private static final String BACKGROUND_BRIGHT_YELLOW = "\033[0;103m";// YELLOW
    private static final String BACKGROUND_BRIGHT_BLUE = "\033[0;104m";// BLUE
    private static final String BACKGROUND_BRIGHT_PURPLE = "\033[0;105m"; // PURPLE
    private static final String BACKGROUND_BRIGHT_CYAN = "\033[0;106m";  // CYAN
    private static final String BACKGROUND_BRIGHT_WHITE = "\033[0;107m";   // WHITE

    public static void info(Object object) {
        info(String.valueOf(object));
    }

    static void info(String message) {
        out.println(BOLD_BRIGHT_GREEN + "[java object memory meter]: " + BRIGHT_GREEN + message + RESET);
    }


    public static void error(String message) {
        err.println(BOLD_BRIGHT_RED + "[java object memory meter]: " + BRIGHT_RED + message + RESET);
    }
}
