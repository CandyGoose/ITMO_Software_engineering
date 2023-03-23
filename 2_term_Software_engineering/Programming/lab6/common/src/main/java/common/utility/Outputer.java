package common.utility;

/**
 * Класс, предоставляющий методы для вывода информации в консоль.
 */
public class Outputer {

    /**
     * Выводит объект в консоль без переноса строки.
     * @param toOut объект для вывода.
     */
    public static void print(Object toOut) {
        System.out.print(toOut);
    }

    /**
     * Выводит перенос строки в консоль.
     */
    public static void println() {
        System.out.println();
    }

    /**
     * Выводит объект в консоль с переносом строки.
     * @param toOut объект для вывода.
     */
    public static void printLn(Object toOut) {
        System.out.println(toOut);
    }

    /**
     * Выводит сообщение об ошибке в консоль.
     * @param toOut объект, содержащий информацию об ошибке.
     */
    public static void printError(Object toOut) {
        System.out.println("error: " + toOut);
    }
}