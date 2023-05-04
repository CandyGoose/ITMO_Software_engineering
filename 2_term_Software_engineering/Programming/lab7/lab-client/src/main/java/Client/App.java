package Client;

import Client.CommandDispatcher.CommandListener;
import Client.CommandDispatcher.CommandToSend;
import Client.CommandDispatcher.CommandValidators;
import Client.NetworkManager.ClientSocketChannelIO;
import Client.util.ScriptReader;
import Common.exception.IllegalSizeOfScriptException;
import Common.exception.WrongAmountOfArgumentsException;
import Common.util.Request;
import Common.util.RequestType;
import Common.util.Response;
import Common.util.TextWriter;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Класс для установки соединения с сервером.
 */
public class App {
    /**
     * Сканнер для чтения команд из скрипта.
     */
    private static Scanner scriptScanner;

    /**
     * Логин.
     */
    public static String login;

    /**
     * Пароль.
     */
    public static String password;

    /**
     * Запускает бесконечный цикл селектора.
     * @param selector объект типа Selector для мониторинга состояний канала
     * @param channel объект типа SocketChannel - канал для передачи данных по сети
     * @param sc объект типа Scanner для чтения ввода пользователя с консоли
     * @param scriptMode флаг режима работы скрипта
     * @throws IOException если произошла ошибка ввода/вывода при работе с каналами или сокетами
     * @throws ClassNotFoundException если класс не найден при десериализации
     * @throws InterruptedException если произошла ошибка в работе потоков
     */
    static void startSelectorLoop(SocketChannel channel, Scanner sc, boolean scriptMode, Selector selector) throws IOException, ClassNotFoundException, InterruptedException {
        do {
            selector.select();
        } while (startIteratorLoop(channel, sc, scriptMode, selector));
    }

    /**
     * Запускает итерационный цикл обработки готовых ключей селектора.
     * @param selector объект типа Selector для мониторинга состояний канала
     * @param channel объект типа SocketChannel - канал для передачи данных по сети
     * @param sc объект типа Scanner для чтения ввода пользователя с консоли
     * @param scriptMode флаг режима работы скрипта
     * @return true, если нужно продолжать работу цикла, false - если необходимо остановиться
     * @throws IOException если произошла ошибка ввода/вывода при работе с каналами или сокетами
     * @throws ClassNotFoundException если класс не найден при десериализации
     * @throws InterruptedException если произошла ошибка в работе потоков
     */
    private static boolean startIteratorLoop(SocketChannel channel, Scanner sc, boolean scriptMode, Selector selector) throws IOException, ClassNotFoundException, InterruptedException {
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isReadable()) {
                SocketChannel clientChannel = (SocketChannel) key.channel();
                ClientSocketChannelIO socketChannelIO = new ClientSocketChannelIO(clientChannel);
                Response response = (Response) socketChannelIO.receive();
                if (response.getInfo() != null) {
                    Client.authorizationModule.validateRegistration(response);
                } else {
                    TextWriter.printInfoMessage(response.getData());
                }
                clientChannel.register(selector, SelectionKey.OP_WRITE);
            } else if (key.isWritable()) {
                try {
                    if (!Client.authorizationModule.isAuthorizationDone()) {
                        Request request = Client.authorizationModule.askForRegistration();
                        SocketChannel client = (SocketChannel) key.channel();

                        ClientSocketChannelIO socketChannelIO = new ClientSocketChannelIO(client);
                        socketChannelIO.send(request);

                        client.register(selector, SelectionKey.OP_READ);
                        continue;
                    }
                    CommandToSend commandToSend = CommandListener.readCommand(sc, scriptMode);
                    if (commandToSend == null) return false;
                    if (commandToSend.getCommandName().equalsIgnoreCase("execute_script")) {
                        CommandValidators.validateAmountOfArgs(commandToSend.getCommandArgs(), 1);
                        ScriptReader scriptReader = new ScriptReader(commandToSend);
                        scriptScanner = new Scanner(scriptReader.getPath());
                        SocketChannel client = (SocketChannel) key.channel();
                        ClientSocketChannelIO socketChannelIO = new ClientSocketChannelIO(client);
                        Request request = new Request("execute_script", RequestType.COMMAND);
                        socketChannelIO.send(request);
                        client.register(selector, SelectionKey.OP_READ);
                        startSelectorLoop(channel, scriptScanner, true, selector);
                        scriptReader.stopScriptReading();
                        startSelectorLoop(channel, sc, false, selector);
                    }
                    Request request = Client.COMMAND_REQUEST_CREATOR.createRequestOfCommand(commandToSend, sc, scriptMode);
                    if (request == null) throw new NullPointerException("");
                    SocketChannel client = (SocketChannel) key.channel();
                    ClientSocketChannelIO socketChannelIO = new ClientSocketChannelIO(client);
                    request.setLogin(login);
                    request.setPassword(password);
                    if (commandToSend.getCommandName().equalsIgnoreCase("exit") && commandToSend.getCommandArgs().length == 0) {
                        try {
                            socketChannelIO.send(request);
                            TextWriter.printInfoMessage("До свидания!");
                            System.exit(0);
                        } catch (Exception e) {
                            TextWriter.printErr("Сервер не доступен. Команда не будет зарегистрирована на сервере.");
                            TextWriter.printInfoMessage("До свидания!");
                            System.exit(0);
                        }
                    } else {
                        socketChannelIO.send(request);
                        client.register(selector, SelectionKey.OP_READ);
                    }
                } catch (NullPointerException | IllegalArgumentException | WrongAmountOfArgumentsException |
                         IllegalSizeOfScriptException e) {
                    TextWriter.printErr(e.getMessage());
                }
            }
        }
        return true;
    }
}
