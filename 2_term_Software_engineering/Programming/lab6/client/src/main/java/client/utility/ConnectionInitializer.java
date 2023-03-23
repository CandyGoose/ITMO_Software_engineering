package client.utility;

import client.App;
import common.exceptions.NotInDeclaredLimitsException;
import common.exceptions.WrongAmountOfElementsException;
import common.utility.Outputer;

/**
 *  ласс дл€ инициализации подключени€ к серверу.
 */
public class ConnectionInitializer {

    /**
     * ¬рем€ ожидани€ перед повторным подключением в миллисекундах
     */
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;

    /**
     * ћаксимальное количество попыток подключени€ к серверу
     */
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;

    /**
     * јдрес сервера
     */
    private String host;

    /**
     * ѕорт сервера
     */
    private int port;

    /**
     *  онструктор класса, который инициализирует адрес подключени€ к серверу.
     * @param hostAndPortArgs массив строк с адресом хоста и номером порта.
     */
    public ConnectionInitializer(String[] hostAndPortArgs) {
        initializeConnectionAddress(hostAndPortArgs);
    }

    /**
     * ћетод дл€ получени€ значени€ переменной host.
     * @return значение переменной host.
     */
    public String getHost() {
        return host;
    }

    /**
     * ћетод дл€ получени€ значени€ переменной port.
     * @return значение переменной port.
     */
    public int getPort() {
        return port;
    }

    /**
     * ћетод дл€ получени€ значени€ переменной MAX_RECONNECTION_ATTEMPTS.
     * @return значение переменной MAX_RECONNECTION_ATTEMPTS.
     */
    public int getMaxReconnectionAttempts() {
        return MAX_RECONNECTION_ATTEMPTS;
    }

    /**
     * ћетод дл€ получени€ значени€ переменной RECONNECTION_TIMEOUT.
     * @return значение переменной RECONNECTION_TIMEOUT.
     */
    public static int getReconnectionTimeout() {
        return RECONNECTION_TIMEOUT;
    }

    /**
     * ћетод, который инициализирует адрес подключени€ к серверу.
     * @param hostAndPortArgs массив строк с адресом хоста и номером порта.
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
            Outputer.printLn("ѕрименение: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            Outputer.printError("ѕорт должен быть представлен целым числом.");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printError("Ќомер порта не может быть отрицательным.");
        }
    }
}
