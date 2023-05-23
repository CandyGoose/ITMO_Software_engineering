package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import common.exceptions.InvalidRequestException;
import common.network.Response;
import common.network.ResponseWithException;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Класс GraphicClientNet представляет графический клиент для сетевого взаимодействия.
 * Он обеспечивает установку соединения, отправку и прием сообщений, а также отключение от сервера.
 */
public class GraphicClientNet {
    /**
     * Время в миллисекундах.
     */
    private static final long SLEEP_TIME = 100;


    /**
     * Объект для обеспечения синхронизации доступа к критическим секциям кода.
     */
    private final Lock lock = new ReentrantLock(true);


    /**
     * Объект, представляющий текущий канал связи.
     * Используется для отслеживания и обновления канала связи.
     */
    private ObjectProperty<ObjectSocketChannelWrapper> channel = new SimpleObjectProperty<>();

    /**
     * Объект, представляющий текущий канал связи.
     * Используется для отслеживания и обновления канала связи.
     */
    public ObjectProperty<ObjectSocketChannelWrapper> channelProperty() {
        return channel;
    }

    /**
     * Устанавливает соединение с указанным адресом.
     *
     * @param address адрес для подключения
     */
    public void connect(InetSocketAddress address) {
        try {
            lock.lock();
            SocketChannel socket = SocketChannel.open();
            socket.connect(address);
            socket.configureBlocking(false);
            channel.set(new ObjectSocketChannelWrapper(socket));
        } catch (UnresolvedAddressException e) {
            showError(LocaleManager.getObservableStringByKey("invalidAddress").get());
            channel.set(null);
        } catch (IOException e) {
            showError(e.getLocalizedMessage());
            channel.set(null);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Разрывает соединение.
     */
    public void disconnect() {
        try {
            lock.lock();
            closeSocket();
        } catch (IOException e) {
            showError(e.getLocalizedMessage());
        } finally {
            channel.set(null);
            lock.unlock();
        }
    }

    /**
     * Закрывает сокетное соединение.
     *
     * @throws IOException при возникновении ошибок ввода-вывода
     */
    public void closeSocket() throws IOException {
        try {
            lock.lock();
            if (channel.get() != null) {
                channel.get().getSocket().close();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Отправляет сообщение на сервер.
     *
     * @param msg объект сообщения
     * @return объект Response, содержащий ответ от сервера
     */
    public Response sendMessage(Object msg) {
        if (channel.get() == null) {
            return null;
        }

        try {
            lock.lock();
            channel.get().clearInBuffer();
            channel.get().sendMessage(msg);

            while (!channel.get().checkForMessage()) {
                Thread.sleep(SLEEP_TIME);
            }

            Object payload = channel.get().getPayload();

            if (payload instanceof Response) {
                Response resp = (Response) payload;
                if (resp instanceof ResponseWithException) {
                    ResponseWithException rwe = (ResponseWithException) resp;
                    if (rwe.getException() instanceof InvalidRequestException) {
                        showError(LocaleManager.getObservableStringByKey(rwe.getException().getLocalizedMessage()).get());
                    } else {
                        showError(rwe.getException().getLocalizedMessage());
                    }
                }
                return resp;
            }
            showError(LocaleManager.getObservableStringByKey("invalidResponse").get());

            return null;
        } catch (IOException | InterruptedException e) {
            Platform.runLater(() -> {
                disconnect();
                showError(e.getLocalizedMessage());
            });
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Отображает сообщение об ошибке на графическом интерфейсе.
     *
     * @param message сообщение об ошибке
     */
    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR, message);
            alert.setTitle(LocaleManager.getObservableStringByKey("error").get());
            alert.setHeaderText(LocaleManager.getObservableStringByKey("networkError").get());
            alert.showAndWait();
        });
    }
}
