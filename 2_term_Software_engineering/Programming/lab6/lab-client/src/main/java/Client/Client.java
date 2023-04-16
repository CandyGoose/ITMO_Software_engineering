package Client;

import Common.util.TextWriter;
import Client.util.ScriptReader;
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

    /**
     * Порт по умолчанию
     */
    private static int PORT = 65435;

    /**
     * Хост, к которому пытается подключиться клиент.
     */
    private static String HOST;

    /**
     * Максимально возможный номер порта.
     */
    private static final int maxPort = 65535;

    /**
     * Сканнер для считывания ввода с консоли.
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Селектор, используемый для отслеживания событий в сокете.
     */
    private static Selector selector;

    /**
     * Режим переподключения, установленный в режим ожидания.
     */
    private static boolean reconnectionMode = false;

    /**
     * Количество неудачных попыток подключения к серверу.
     */
    private static int attempts = 0;
    public static void main(String[] args) {
        try {
            if (!reconnectionMode) {
                inputPort();
            } else {
                Thread.sleep(7 * 1000); // 7 секунд на переподключение
            }
            SocketChannel clientChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            TextWriter.printInfoMessage("Клиент подключен.");
            attempts = 0;
            clientChannel.configureBlocking(false);
            selector = Selector.open();
            clientChannel.register(selector, SelectionKey.OP_WRITE);
            ClientApp.startSelectorLoop(selector, clientChannel, SCANNER, false);
        } catch (ClassNotFoundException e) {
            TextWriter.printErr("Попытка сериализовать несериализуемый объект.");
        } catch (InterruptedException e) {
            TextWriter.printErr("Соединение было прервано во время бездействия. Перезапуск клиента.");
        } catch (UnresolvedAddressException e) {
            TextWriter.printErr("Сервер с этим хостом не найден. Попробуйте снова.");
            main(args);
        } catch (IOException e) {
            TextWriter.printErr("Сервер недоступен. Переподключение, попытка #" + (attempts + 1));
            reconnectionMode = true;
            if (attempts == 4) {
                TextWriter.printErr("Переподключение не удалось. Попробуйте подключиться позднее.");
                System.exit(0);
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
    private static void inputPort() {
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
                    } else {
                        TextWriter.printErr("Число не входит в установленные пределы, повторите ввод.");
                        inputPort();
                    }
                } catch (IllegalArgumentException e) {
                    TextWriter.printErr("Ошибка при обработке номера, повторите ввод.");
                    inputPort();
                }
            } else if (!"y".equals(s)) {
                TextWriter.printErr("Вы ввели недопустимый символ, попробуйте еще раз.");
                inputPort();
            }
        } catch (NoSuchElementException e) {
            TextWriter.printErr("Принудительное завершение работы.");
            System.exit(1);
        }
    }
}