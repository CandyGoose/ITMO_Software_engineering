package Client;

import Client.CommandDispatcher.CommandListener;
import Client.CommandDispatcher.CommandToSend;
import Client.CommandDispatcher.CommandValidators;
import Client.util.ClientSocketChannelIO;
import Client.util.RequestCreator;
import Client.util.ScannerManager;
import Client.util.ScriptReader;
import Common.exception.IllegalSizeOfScriptException;
import Common.exception.IncorrectInputInScriptException;
import Common.exception.WrongAmountOfArgumentsException;
import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

public class ClientApp {
    private static Scanner scriptScanner;
    private static final RequestCreator requestCreator = new RequestCreator();
    static void startSelectorLoop(Selector selector, SocketChannel channel, Scanner sc, boolean scriptMode) throws IOException, ClassNotFoundException, InterruptedException {
        do {
            selector.select();
        } while (startIteratorLoop(selector, channel, sc, scriptMode));
    }

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
                    if (commandToSend.getCommandName().equalsIgnoreCase("add") && scriptMode) {
                        CommandValidators.validateAmountOfArgs(commandToSend.getCommandArgs(), 0);
                        if (scriptScanner == null) {
                            throw new IllegalStateException("Сканер скриптов не установлен.");
                        }
                        ScannerManager scannerManager = new ScannerManager(scriptScanner);
                        scannerManager.setScriptScanner(scriptScanner);
                        Request request = new Request("add", scannerManager.askOrganization());
                        SocketChannel client = (SocketChannel) key.channel();
                        ClientSocketChannelIO socketChannelIO = new ClientSocketChannelIO(client);
                        socketChannelIO.send(request);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (commandToSend.getCommandName().equalsIgnoreCase("update") && scriptMode) {
                        CommandValidators.validateAmountOfArgs(commandToSend.getCommandArgs(), 1);
                        if (scriptScanner == null) {
                            throw new IllegalStateException("Сканер скриптов не установлен.");
                        }
                        ScannerManager scannerManager = new ScannerManager(scriptScanner);
                        scannerManager.setScriptScanner(scriptScanner);
                        String[] commandsArgs = (commandToSend.getCommandArgs());
                        Long arg = Long.parseLong(commandsArgs [0]);
                        Request request = new Request("update", arg, scannerManager.askOrganization());
                        SocketChannel client = (SocketChannel) key.channel();
                        ClientSocketChannelIO socketChannelIO = new ClientSocketChannelIO(client);
                        socketChannelIO.send(request);
                        client.register(selector, SelectionKey.OP_READ);
                    } else {
                        Request request = requestCreator.createRequestOfCommand(commandToSend);
                        SocketChannel client = (SocketChannel) key.channel();
                        ClientSocketChannelIO socketChannelIO = new ClientSocketChannelIO(client);
                        if (commandToSend.getCommandName().equalsIgnoreCase("exit")) {
                            try {
                                socketChannelIO.send(request);
                                TextWriter.printInfoMessage("До свидания!");
                                System.exit(0);
                            } catch (Exception e) {
                                TextWriter.printErr("Сервер не доступен.\nКоманда не будет зарегистрирована на сервере.");
                                TextWriter.printInfoMessage("До свидания!");
                                System.exit(0);
                            }
                        } else {
                            if (request == null) throw new NullPointerException("");
                            socketChannelIO.send(request);
                            client.register(selector, SelectionKey.OP_READ);
                        }
                    }
                } catch (NullPointerException | IllegalArgumentException | WrongAmountOfArgumentsException |
                         IllegalSizeOfScriptException e) {
                    TextWriter.printErr(e.getMessage());
                } catch (IncorrectInputInScriptException e) {
                    TextWriter.printErr("Проверьте верность данных в скрипте.");
                    System.exit(1);
                }
            }
        }
        return true;
    }
}
