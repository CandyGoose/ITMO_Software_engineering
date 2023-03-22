package server.utility;

/**
 * ���������� ������ ��� ������ � ������� �������.
 */
public class ResponseOutputer {

    /**
     * ������ StringBuilder, ������������ ��� ���������� ������ ������.
     */
    private static final StringBuilder stringBuilder = new StringBuilder();

    /**
     * ��������� ������ � ����� ������ ������.
     * @param toOut ������, ������� ���������� ��������.
     */
    public static void append(Object toOut) {
        stringBuilder.append(toOut);
    }

    /**
     * ��������� ������� �� ����� ������ � ����� ������ ������.
     */
    public static void appendLn() {
        stringBuilder.append("\n");
    }

    /**
     * ��������� ������ � ������� ������ � ����� ������ ������.
     * @param toOut ������, ������� ���������� ��������.
     */
    public static void appendLn(Object toOut) {
        stringBuilder.append(toOut).append("\n");
    }

    /**
     * ��������� ������ � ����� ������ ������ � ������� "error: ������".
     * @param toOut ������, ������� ���������� ��������.
     */
    public static void appendError(Object toOut) {
        stringBuilder.append("error: ").append(toOut).append("\n");
    }

    /**
     * ��������� ��� ������� � ���� ������� � ��������������� ������.
     * @param element1 ������ ������� �������.
     * @param element2 ������ ������� �������.
     */
    public static void appendTable(Object element1, Object element2) {
        stringBuilder.append(String.format("%-37s%-1s%n", element1, element2)); // - ������ ������������ �� ������ ����, 37 - ������ ������� ������� � 37 ��������, 1 - �������, s - ��� ������ ������, � %n ��������� ������� ������
    }

    /**
     * ���������� ������� ������ ������.
     * @return ������ ������.
     */
    public static String getString() {
        return stringBuilder.toString();
    }

    /**
     * ���������� ������� ������ ������ � ������� �.
     * @return ������ ������.
     */
    public static String getAndClear() {
        String toReturn = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return toReturn;
    }
}