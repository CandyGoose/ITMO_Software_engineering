package common.exceptions;

/**
 * ����������, ������������� ��� ������ ������������� �������.
 */
public class CommandUsageException extends Exception {
    /**
     * ������� ���������� ��� ��������� �� ������.
     */
    public CommandUsageException() {
        super();
    }

    /**
     * ������� ���������� � �������� ���������� �� ������.
     * @param message ��������� �� ������
     */
    public CommandUsageException(String message) {
        super(message);
    }
}