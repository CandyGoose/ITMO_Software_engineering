package server.utility;

/**
 * Обработчик текста для вывода в консоль сервера.
 */
public class ResponseOutputer {

    /**
     * Объект StringBuilder, используемый для построения строки ответа.
     */
    private static final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Добавляет объект в конец строки вывода.
     * @param toOut объект, который необходимо добавить.
     */
    public static void append(Object toOut) {
        stringBuilder.append(toOut);
    }

    /**
     * Добавляет перевод на новую строку в конец строки вывода.
     */
    public static void appendLn() {
        stringBuilder.append("\n");
    }

    /**
     * Добавляет объект и перевод строки в конец строки вывода.
     * @param toOut объект, который необходимо добавить.
     */
    public static void appendLn(Object toOut) {
        stringBuilder.append(toOut).append("\n");
    }

    /**
     * Добавляет объект в конец строки вывода в формате "error: объект".
     * @param toOut объект, который необходимо добавить.
     */
    public static void appendError(Object toOut) {
        stringBuilder.append("error: ").append(toOut).append("\n");
    }

    /**
     * Добавляет два объекта в виде таблицы с форматированием ширины.
     * @param element1 первый элемент таблицы.
     * @param element2 второй элемент таблицы.
     */
    public static void appendTable(Object element1, Object element2) {
        stringBuilder.append(String.format("%-37s%-1s%n", element1, element2)); // - задает выравнивание по левому краю, 37 - ширина первого столбца в 37 символов, 1 - второго, s - тип данных строка, а %n добавляет перенос строки
    }

    /**
     * Возвращает текущую строку вывода.
     * @return строка вывода.
     */
    public static String getString() {
        return stringBuilder.toString();
    }

    /**
     * Возвращает текущую строку вывода и очищает её.
     * @return строка вывода.
     */
    public static String getAndClear() {
        String toReturn = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return toReturn;
    }
}