package server.utility;


public class ResponseOutputer {
    private static final StringBuilder stringBuilder = new StringBuilder();

    public static void append(Object toOut) {
        stringBuilder.append(toOut);
    }

    public static void appendLn() {
        stringBuilder.append("\n");
    }

    public static void appendLn(Object toOut) {
        stringBuilder.append(toOut).append("\n");
    }

    public static void appendError(Object toOut) {
        stringBuilder.append("error: ").append(toOut).append("\n");
    }

    public static void appendTable(Object element1, Object element2) {
        stringBuilder.append(String.format("%-37s%-1s%n", element1, element2));
    }

    public static String getString() {
        return stringBuilder.toString();
    }

    public static String getAndClear() {
        String toReturn = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return toReturn;
    }

    public static void clear() {
        stringBuilder.delete(0, stringBuilder.length());
    }
}