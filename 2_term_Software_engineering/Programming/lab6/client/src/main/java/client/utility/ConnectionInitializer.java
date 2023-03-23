package client.utility;

import client.App;
import common.exceptions.NotInDeclaredLimitsException;
import common.exceptions.WrongAmountOfElementsException;
import common.utility.Outputer;

/**
 * ����� ��� ������������� ����������� � �������.
 */
public class ConnectionInitializer {

    /**
     * ����� �������� ����� ��������� ������������ � �������������
     */
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;

    /**
     * ������������ ���������� ������� ����������� � �������
     */
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;

    /**
     * ����� �������
     */
    private String host;

    /**
     * ���� �������
     */
    private int port;

    /**
     * ����������� ������, ������� �������������� ����� ����������� � �������.
     * @param hostAndPortArgs ������ ����� � ������� ����� � ������� �����.
     */
    public ConnectionInitializer(String[] hostAndPortArgs) {
        initializeConnectionAddress(hostAndPortArgs);
    }

    /**
     * ����� ��� ��������� �������� ���������� host.
     * @return �������� ���������� host.
     */
    public String getHost() {
        return host;
    }

    /**
     * ����� ��� ��������� �������� ���������� port.
     * @return �������� ���������� port.
     */
    public int getPort() {
        return port;
    }

    /**
     * ����� ��� ��������� �������� ���������� MAX_RECONNECTION_ATTEMPTS.
     * @return �������� ���������� MAX_RECONNECTION_ATTEMPTS.
     */
    public int getMaxReconnectionAttempts() {
        return MAX_RECONNECTION_ATTEMPTS;
    }

    /**
     * ����� ��� ��������� �������� ���������� RECONNECTION_TIMEOUT.
     * @return �������� ���������� RECONNECTION_TIMEOUT.
     */
    public static int getReconnectionTimeout() {
        return RECONNECTION_TIMEOUT;
    }

    /**
     * �����, ������� �������������� ����� ����������� � �������.
     * @param hostAndPortArgs ������ ����� � ������� ����� � ������� �����.
     */
    private void initializeConnectionAddress(String[] hostAndPortArgs) {
        try {
            /* if (hostAndPortArgs.length != 2) throw new WrongAmountOfElementsException();
            host = hostAndPortArgs[0];
            port = Integer.parseInt(hostAndPortArgs[1]);*/

            if (hostAndPortArgs.length != 0) throw new WrongAmountOfElementsException();
            host = "localhost";
            port = 8080;

            if (port < 0) throw new NotInDeclaredLimitsException();
        } catch (WrongAmountOfElementsException exception) {
            String jarName = new java.io.File(App.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            Outputer.printLn("����������: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            Outputer.printError("���� ������ ���� ����������� ����� ������.");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printError("����� ����� �� ����� ���� �������������.");
        }
    }
}
