package client.views;

import java.io.IOException;
import java.net.InetSocketAddress;

import client.GraphicClient;

import client.util.LocaleManager;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * Класс, представляющий представление подключения.
 */
public class ConnectionController {
    private static final int MAX_PORT = 65565;
    private final Parent view;
    @FXML
    private Label addressLabel;
    @FXML
    private TextField addressField;
    @FXML
    private Label portLabel;
    @FXML
    private TextField portField;
    @FXML
    private Button connectButton;
    private final GraphicClient client;

    /**
     * Конструктор класса ConnectionView.
     *
     * @param client клиентская графическая программа
     * @throws IOException если возникает ошибка ввода-вывода при загрузке представления
     */
    public ConnectionController(GraphicClient client) throws IOException {
        this.client = client;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ConnectionView.fxml"));
        fxmlLoader.setController(this);
        view = fxmlLoader.load();
        initialize();
    }

    /**
     * Получение представления.
     *
     * @return представление
     */
    public Parent getView() {
        return view;
    }

    @FXML
    private void initialize() {
        connectButton.textProperty().bind(LocaleManager.getObservableStringByKey("connectButton"));
        addressLabel.textProperty().bind(LocaleManager.getObservableStringByKey("addressLabel"));
        addressField.promptTextProperty().bind(LocaleManager.getObservableStringByKey("addressPrompt"));
        portLabel.textProperty().bind(LocaleManager.getObservableStringByKey("portLabel"));
        portField.promptTextProperty().bind(LocaleManager.getObservableStringByKey("portPrompt"));

        addressField.setText("localhost");
        portField.setText("65435");
        portField.setTextFormatter(new TextFormatter<>(change -> {
            if (!change.getControlNewText().equals("")) {
                try {
                    int port = Integer.parseInt(change.getControlNewText());
                    if (port < 0 || port > MAX_PORT) {
                        change.setText("");
                        change.setRange(0, 0);
                    }
                } catch (NumberFormatException e) {
                    change.setText("");
                    change.setRange(0, 0);
                }
            }
            return change;
        }));
        connectButton.disableProperty().bind(Bindings.or(addressField.textProperty().isEmpty(), portField.textProperty().isEmpty()));
        connectButton.setOnAction(e -> client.getNetwork().connect(new InetSocketAddress(addressField.getText(), Integer.parseInt(portField.getText()))));
    }
}
