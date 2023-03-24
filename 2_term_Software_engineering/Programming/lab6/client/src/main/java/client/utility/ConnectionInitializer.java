package client.utility;

import client.App;
import common.exceptions.NotInDeclaredLimitsException;
import common.exceptions.WrongAmountOfElementsException;
import common.utility.Outputer;

/**
 * ????? ??? ????????????? ??????????? ? ???????.
 */
public class ConnectionInitializer {

    /**
     * Время ожидания перед повторным подключением в миллисекундах
     */
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;

    /**
     * Максимальное количество попыток подключения к серверу
     */
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;

    /**
     * Адрес сервера
     */
    private String host;

    /**
     * Порт сервера
     */
    private int port;

    /**
     * Метод инициализирует адрес подключения к серверу, используя аргументы командной строки.
     * @param hostAndPortArgs массив строк с адресом сервера и номером порта.
     */
    public ConnectionInitializer(String[] hostAndPortArgs) {
        initializeConnectionAddress(hostAndPortArgs);
    }

    /**
     * Метод для получения значения поля host.
     * @return строковое значение поля host.
     */
    public String getHost() {
        return host;
    }

    /**
     * Метод для получения значения поля port.
     * @return целочисленное значение поля port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Метод для получения значения константы MAX_RECONNECTION_ATTEMPTS.
     * @return целочисленное значение константы MAX_RECONNECTION_ATTEMPTS.
     */
    public int getMaxReconnectionAttempts() {
        return MAX_RECONNECTION_ATTEMPTS;
    }

    /**
     * Статический метод для получения значения константы RECONNECTION_TIMEOUT.
     * @return целочисленное значение константы RECONNECTION_TIMEOUT.
     */
    public static int getReconnectionTimeout() {
        return RECONNECTION_TIMEOUT;
    }

    /**
     * Приватный метод для инициализации значений полей host и port.
     * @param hostAndPortArgs массив строк, содержащий значения хоста и порта.
     */
    private void initializeConnectionAddress(String[] hostAndPortArgs) {
        try {
            /* if (hostAndPortArgs.length != 2) throw new WrongAmountOfElementsException();
            host = hostAndPortArgs[0];
            port = Integer.parseInt(hostAndPortArgs[1]); */

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
            Outputer.printLn("Формат использования: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            Outputer.printError("Порт должен быть целым числом.");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printError("Порт не может быть отрицательным.");
        }
    }
}
