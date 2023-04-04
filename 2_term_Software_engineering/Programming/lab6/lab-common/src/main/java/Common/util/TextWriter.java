package Common.util;

import java.io.PrintStream;

public class TextWriter {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final PrintStream printStream = System.out;

    public static void printInfoMessage(String message) {
        printStream.println(message);
    }

    public static void printErr(String message) {
        printStream.println(ANSI_RED + message + ANSI_RESET);
    }

    public static String getWhiteText(String text) {
        return text;
    }

    public static String getRedText(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }
}
