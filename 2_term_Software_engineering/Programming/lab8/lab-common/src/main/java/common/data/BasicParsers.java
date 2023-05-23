package common.data;

import common.exceptions.InvalidFieldException;
import common.network.BasicUserIO;
import common.util.TerminalColors;

/**
 * Утилитарный класс с базовыми парсерами данных.
 */
public final class BasicParsers {
    /**
     * Новый объект парсера
     */
    private BasicParsers() {
    }

    /**
     * Парсит значение типа Long на основе пользовательского ввода.
     *
     * @param io объект для ввода-вывода пользовательских данных
     * @param prompt приглашение для ввода
     * @param errorMsg сообщение об ошибке при некорректном вводе
     * @return значение типа Long или null, если ввод пустой
     * @throws InvalidFieldException если введенные данные некорректны
     */
    public static Long parseLong(BasicUserIO io, String prompt, String errorMsg) throws InvalidFieldException {
        try {
            String input = io.read(
                    TerminalColors.colorString(prompt, TerminalColors.BLUE) + '\n'
            );

            if (input == null || input.trim().isEmpty()) {
                return null;
            }

            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(errorMsg, e);
        }
    }

    /**
     * Парсит значение типа Float на основе пользовательского ввода.
     *
     * @param io объект для ввода-вывода пользовательских данных
     * @param prompt приглашение для ввода
     * @param errorMsg сообщение об ошибке при некорректном вводе
     * @return значение типа Float или null, если ввод пустой
     * @throws InvalidFieldException если введенные данные некорректны
     */
    public static Float parseFloat(BasicUserIO io, String prompt, String errorMsg) throws InvalidFieldException {
        try {
            String input = io.read(
                TerminalColors.colorString(prompt, TerminalColors.BLUE) + '\n'
            );

            if (input == null || input.trim().isEmpty()) {
                return null;
            }

            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(errorMsg, e);
        }
    }

    /**
     * Парсит значение типа String на основе пользовательского ввода.
     *
     * @param io объект для ввода-вывода пользовательских данных
     * @param prompt приглашение для ввода
     * @return значение типа String или null, если ввод пустой
     */
    public static String parseString(BasicUserIO io, String prompt) {
        String input = io.read(
            TerminalColors.colorString(prompt, TerminalColors.BLUE) + '\n'
        );

        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        return input;
    }

    /**
     * Вспомогательный класс, обеспечивающий повторение парсинга до получения корректного значения.
     */
    public static class Repeater {
        /**
         * Повторяет парсинг значения до получения корректного значения.
         *
         * @param parser парсер значения
         * @param io объект для ввода-вывода пользовательских данных
         * @param <T> тип значения
         * @return корректное значение
         */
        public static <T> T doUntilGet(Parser<T> parser, BasicUserIO io) {
            do {
                try {
                    return parser.parse(io);
                } catch (InvalidFieldException e) {
                    io.writeln("Please try again.");
                }
            } while (true);
        }
    }

    /**
     * Интерфейс для парсеров значений.
     *
     * @param <T> тип значения
     */
    public interface Parser<T> {
        /**
         * Парсит значение на основе пользовательского ввода.
         *
         * @param io объект для ввода-вывода пользовательских данных
         * @return значение типа T
         * @throws InvalidFieldException если введенные данные некорректны
         */
        T parse(BasicUserIO io) throws InvalidFieldException;
    }
}
