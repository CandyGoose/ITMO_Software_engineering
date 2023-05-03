package Server;

import Common.util.Request;
import Common.util.Response;
import Server.requestHandlers.RequestExecutor;
import Server.requestHandlers.RequestReader;
import Server.requestHandlers.ResponseSender;
import Server.util.ConsoleThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class App {
    private volatile Selector selector;

    private final ExecutorService fixedService;
    private final ExecutorService fixedService2;
    private final ExecutorService fixedService3;

    private final Set<SelectionKey> workingKeys =
            Collections.synchronizedSet(new HashSet<>());
    private final ServerSocketChannel server;

    @Autowired
    public App(ServerSocketChannel server,
               @Qualifier("fixedService") ExecutorService fixedService,
               @Qualifier("fixedService2") ExecutorService fixedService2,
               @Qualifier("fixedService3") ExecutorService fixedService3) {
        this.server = server;
        this.fixedService = fixedService;
        this.fixedService2 = fixedService2;
        this.fixedService3 = fixedService3;
    }

    public void start() {
        ConsoleThread consoleThread = new ConsoleThread();
        if (ServerConfig.isRunning) {
            consoleThread.start();
            startServer();
        }
        fixedService.shutdown();
        fixedService2.shutdown();
        fixedService3.shutdown();
    }

    private void startServer() {
        ServerConfig.logger.info("Сервер запущен.");
        try {
            selector = Selector.open();
            ServerSocketChannel server = initChannel(selector);
            startSelectorLoop(server);
        } catch (IOException e) {
            ServerConfig.logger.severe("Проблемы с IO.");
            ServerConfig.toggleStatus();
        } catch (ClassNotFoundException e) {
            ServerConfig.logger.warning("Попытка сериализовать несериализуемый объект.");
            ServerConfig.toggleStatus();
        }
    }

    private void startSelectorLoop(ServerSocketChannel channel) throws IOException, ClassNotFoundException {
        while (channel.isOpen() && ServerConfig.isRunning) {
            if (selector.select(1) != 0) {
                startIteratorLoop(channel);
            }
        }
    }
    private void startIteratorLoop(ServerSocketChannel channel) throws IOException {
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isValid() && !workingKeys.contains(key)) {
                if (key.isAcceptable()) {
                    accept(channel);
                } else if (key.isReadable()) {
                    workingKeys.add(key);
                    ServerConfig.logger.info("Клиент " + ((SocketChannel) key.channel()).getLocalAddress() + " прислал сообщение.");
                    Supplier<Request> requestReader = new RequestReader(key);
                    Function<Request, Response> requestExecutor = new RequestExecutor();
                    Consumer<Response> responseSender = new ResponseSender(key, workingKeys);
                    CompletableFuture
                            .supplyAsync(requestReader, fixedService)
                            .thenApplyAsync(requestExecutor, fixedService2)
                            .thenAcceptAsync(responseSender, fixedService3);
                }
            }
        }
    }

    private void accept(ServerSocketChannel channel) throws IOException {
        SocketChannel socketChannel = channel.accept();
        ServerConfig.logger.info("Сервер получил соединение от " + socketChannel.getLocalAddress());
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private ServerSocketChannel initChannel(Selector selector) throws IOException {
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        return server;
    }
}
