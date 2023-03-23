package common.interaction;

import java.io.Serializable;

/**
 * �����, �������������� ������ �� ���������� ������� � ������� ��������������.
 * ��������� ��������� Serializable ��� ����������� �������� ������� �� ����.
 */
public class Request implements Serializable {
    /**
     * ��� �������
     */
    private final String commandName;
    /**
     * ��������� �������� �������
     */
    private final String commandStringArgument;
    /**
     * ��������� �������� �������
     */
    private final Serializable commandObjectArgument;

    /**
     * ����������� ������ Request � ��������� ����������.
     * @param commandName ��� �������.
     * @param commandStringArgument ��������� �������� �������.
     * @param commandObjectArgument ��������� �������� �������.
     */
    public Request(String commandName, String commandStringArgument, Serializable commandObjectArgument) {
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.commandObjectArgument = commandObjectArgument;
    }

    /**
     * ����������� ������ Request ��� ���������� ���������.
     * @param commandName ��� �������.
     * @param commandStringArgument ��������� �������� �������.
     */
    public Request(String commandName, String commandStringArgument) {
        this(commandName, commandStringArgument, null);
    }

    /**
     * ����������� ������ Request ��� ����������.
     */
    public Request() {
        this("","");
    }

    /**
     * ���������� ��� �������.
     * @return ��� �������.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * ���������� ��������� �������� �������.
     * @return ��������� �������� �������.
     */
    public String getCommandStringArgument() {
        return commandStringArgument;
    }

    /**
     * ���������� ��������� �������� �������.
     * @return ��������� �������� �������.
     */
    public Object getCommandObjectArgument() {
        return commandObjectArgument;
    }

    /**
     * ���������, �������� �� ������ ������ (�� �������� ����������).
     * @return true, ���� ������ ������, ����� - false.
     */
    public boolean isEmpty() {
        return commandName.isEmpty() && commandStringArgument.isEmpty() && commandObjectArgument == null;
    }

    /**
     * ���������� ��������� ������������� ������� Request.
     * @return ��������� ������������� ������� Request.
     */
    @Override
    public String toString() {
        return "Request[" + commandName + ", " + commandStringArgument + ", " + commandObjectArgument + "]";
    }
}
