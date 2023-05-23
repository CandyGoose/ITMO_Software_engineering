package common.util;

/**
 * Перечисление, представляющее цвета терминала.
 */
public enum TerminalColors {
    /**
     * Цвета
     */
    RESET("\u001b"), GREEN("\u001b"), BLUE("\u001b"), RED("\u001b");

    /**
     * Флаг для включения или отключения окрашивания.
     */
    private static boolean doColoring = true;

    /**
     * Код ANSI
     */
    private final String ansiCode;

    /**
     * Конструктор перечисления TerminalColors.
     *
     * @param ansiCode код ANSI для соответствующего цвета.
     */
    TerminalColors(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    /**
     * Возвращает строковое представление кода.
     *
     * @return строковое представление кода.
     */
    public String toString() {
        return ansiCode;
    }


    /**
     * Устанавливает флаг для включения или отключения окрашивания.
     *
     * @param flag флаг для включения или отключения окрашивания.
     */
    public static void doColoring(boolean flag) {
        doColoring = flag;
    }

    /**
     * Окрашивает заданную строку указанным цветом, если окрашивание включено.
     *
     * @param s строка для окрашивания.
     * @param color цвет для окрашивания.
     * @return окрашенная строка, если окрашивание включено, иначе исходная строка.
     */
    public static String colorString(String s, TerminalColors color) {
        if (doColoring) {
            StringBuilder sb = new StringBuilder();
            sb.append(color);
            sb.append(s);
            sb.append(RESET);
            return sb.toString();
        } else {
            return s;
        }
    }
}
