package Client;

import Client.CommandDispatcher.CommandListener;
import Client.CommandDispatcher.CommandToSend;
import Client.CommandDispatcher.CommandValidators;
import Client.NetworkManager.ClientSocketChannelIO;
import Client.NetworkManager.CommandRequestCreator;
import Client.NetworkManager.AuthorizationModule;
import Client.util.ScriptReader;
import Common.exception.IllegalSizeOfScriptException;
import Common.exception.WrongAmountOfArgumentsException;
import Common.util.Request;
import Common.util.RequestType;
import Common.util.Response;
import Common.util.TextWriter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.*;

/**
 * Главный класс, запускающий приложение клиента.
 * Содержит метод main, отвечающий за запуск приложения.
 *
 * @author Касьяненко Вера (P3120)
 */
public final class Client {
    private Client() {
        throw new UnsupportedOperationException("Это утилитный класс, экземпляр которого не может быть создан.");
    }
    /**
     * Порт по умолчанию для соединения.
     */
    private static int PORT = 65435;

    /**
     * Адрес хоста для соединения.
     */
    private static String HOST;

    /**
     * Максимально допустимый порт для соединения.
     */
    private static final int maxPort = 65535;

    /**
     * Сканнер для ввода данных с консоли.
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Канал для обмена данными с сервером.
     */
    private static SocketChannel clientChannel;

    /**
     * Объект для создания запросов к серверу.
     */
    static final CommandRequestCreator COMMAND_REQUEST_CREATOR = new CommandRequestCreator();

    /**
     * Модуль авторизации.
     */
    static final AuthorizationModule authorizationModule = new AuthorizationModule(SCANNER);

    /**
     * Режим переподключения.
     */
    private static boolean reconnectionMode = false;

    /**
     * Количество попыток подключения.
     */
    private static int attempts = 0;

    public static void main(String[] args) {

        try {
            if (!reconnectionMode) {
                inputAddress();
            } else {
                Thread.sleep(7 * 1000);
                clientChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            }
            TextWriter.printInfoMessage("Клиент подключен.");
            clientChannel.configureBlocking(false);
            Selector selector = Selector.open();
            clientChannel.register(selector, SelectionKey.OP_WRITE);
            attempts = 0;
            App.startSelectorLoop(clientChannel, SCANNER, false, selector);
        } catch (ClassNotFoundException e) {
            TextWriter.printErr("Попытка сериализовать несериализуемый объект.");
        } catch (InterruptedException e) {
            TextWriter.printErr("Соединение было прервано во время бездействия. Перезапустите клиента.");
        } catch (UnresolvedAddressException e) {
            TextWriter.printErr("Сервер с этим хостом не найден. Попробуйте снова.");
            main(args);
        } catch (IOException e) {
            TextWriter.printErr("Сервер недоступен. Переподключение, попытка #" + (attempts + 1));
            reconnectionMode = true;
            if (attempts == 4) {
                TextWriter.printErr("Переподключение не удалось. Попробуйте подключиться позднее.");
                System.exit(1);
            }
            attempts++;
            ScriptReader.callStack.clear();
            main(args);
        } catch (NoSuchElementException e) {
            TextWriter.printErr("Принудительное завершение работы.");
            System.exit(1);
        }
    }

    /**
     * Метод inputPort запрашивает у пользователя данные для создания соединения.
     */
    private static void inputAddress() {
        TextWriter.printInfoMessage("Введите имя хоста:");
        try {
            HOST = SCANNER.nextLine();
        } catch (NoSuchElementException e) {
            TextWriter.printErr("Принудительное завершение работы.");
            System.exit(1);
        }
        TextWriter.printInfoMessage("Вы хотите использовать порт по умолчанию? [y/n]");
        try {
            String s = SCANNER.nextLine().trim().toLowerCase(Locale.ROOT);
            if ("n".equals(s)) {
                TextWriter.printInfoMessage("Введите порт удаленного хоста (1-65535):");
                String port = SCANNER.nextLine();
                try {
                    int portInt = Integer.parseInt(port);
                    if (portInt > 0 && portInt <= maxPort) {
                        PORT = portInt;
                        clientChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
                    } else {
                        TextWriter.printErr("Число не входит в установленные пределы, повторите ввод.");
                        inputAddress();
                    }
                } catch (IllegalArgumentException e) {
                    TextWriter.printErr("Ошибка при обработке номера, повторите ввод.");
                    inputAddress();
                }
            } else if ("y".equals(s)) {
                clientChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            } else {
                TextWriter.printErr("Вы ввели недопустимый символ, попробуйте еще раз.");
                inputAddress();
            }
        } catch (NoSuchElementException e) {
            TextWriter.printErr("Принудительное завершение работы.");
            System.exit(1);
        } catch (UnresolvedAddressException e) {
            TextWriter.printErr("Сервер с этим хостом не найден. Попробуйте снова.");
            inputAddress();
        } catch (IOException e) {
            TextWriter.printErr("Сервер недоступен.");
            inputAddress();
        }
    }
}
