package Common.util;

import java.io.PrintStream;

/**
 * Класс TextWriter предназначен для вывода текстовых сообщений в консоль.
 */
public class TextWriter {

    /**
     * Код для сброса цветовой схемы текста.
     */
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * Код для установки красного цвета шрифта.
     */
    private static final String ANSI_RED = "\u001B[31m";

    /**
     * Объект вывода текста в консоль.
     */
    private static final PrintStream printStream = System.out;

    /**
     * Выводит в консоль информационное сообщение.
     *
     * @param message строка, содержащая текст информационного сообщения.
     */
    public static void printInfoMessage(String message) {
        printStream.println(message);
    }

    /**
     * Выводит в консоль сообщение об ошибке.
     *
     * @param message строка, содержащая текст сообщения об ошибке.
     */
    public static void printErr(String message) {
        printStream.println(ANSI_RED + message + ANSI_RESET);
    }

    /**
     * Возвращает строку с белым текстом.
     *
     * @param text строка, которую нужно отобразить белым цветом
     * @return строка с белым текстом
     */
    public static String getWhiteText(String text) {
        return text;
    }

    /**
     * Возвращает строку с красным текстом.
     *
     * @param text строка, которую нужно отобразить красным цветом.
     * @return строка с красным текстом
     */
    public static String getRedText(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }
}
