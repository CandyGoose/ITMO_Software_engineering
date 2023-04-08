package Client;

import Client.CommandDispatcher.CommandListener;
import Client.CommandDispatcher.CommandToSend;
import Client.CommandDispatcher.CommandValidators;
import Client.util.ClientSocketChannelIO;
import Client.util.RequestCreator;
import Client.util.ScriptReader;
import Common.exception.IllegalSizeOfScriptException;
import Common.exception.WrongAmountOfArgumentsException;
import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Класс для установки соединения с сервером.
 */
public class ClientApp {

    /**
     * Сканнер для чтения команд из скрипта.
     */
    private static Scanner scriptScanner;

    /**
     * Создатель запросов для отправки на сервер.
     */
    private static final RequestCreator requestCreator = new RequestCreator();

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
    static void startSelectorLoop(Selector selector, SocketChannel channel, Scanner sc, boolean scriptMode) throws IOException, ClassNotFoundException, InterruptedException {
        do {
            selector.select();
        } while (startIteratorLoop(selector, channel, sc, scriptMode));
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
    private static boolean startIteratorLoop(Selector selector, SocketChannel channel, Scanner sc, boolean scriptMode) throws IOException, ClassNotFoundException, InterruptedException {
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isReadable()) {
                SocketChannel clientChannel = (SocketChannel) key.channel();
                ClientSocketChannelIO socketChannelIO = new ClientSocketChannelIO(clientChannel);
                Response response = (Response) socketChannelIO.receive();
                TextWriter.printInfoMessage(response.getData());
                clientChannel.register(selector, SelectionKey.OP_WRITE);
            } else if (key.isWritable()) {
                try {
                    CommandToSend commandToSend = CommandListener.readCommand(sc, scriptMode);
                    if (commandToSend == null) return false;
                    if (commandToSend.getCommandName().equalsIgnoreCase("execute_script")) {
                        CommandValidators.validateAmountOfArgs(commandToSend.getCommandArgs(), 1);
                        ScriptReader scriptReader = new ScriptReader(commandToSend);
                        scriptScanner = new Scanner(scriptReader.getPath());
                        startSelectorLoop(selector, channel, scriptScanner, true);
                        scriptReader.stopScriptReading();
                        startSelectorLoop(selector, channel, sc, false);
                    }
                    Request request = requestCreator.createRequestOfCommand(commandToSend);
                    SocketChannel client = (SocketChannel) key.channel();
                    ClientSocketChannelIO socketChannelIO = new ClientSocketChannelIO(client);
                    if (commandToSend.getCommandName().equalsIgnoreCase("exit")) {
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
                        if (request == null) throw new NullPointerException("");
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
