package client.views;

import java.net.InetSocketAddress;

import client.GraphicClient;
import client.LocaleManager;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

/**
 * Класс представления подключения к серверу.
 */
public class ConnectionView {
    private static final int MAX_PORT = 65565;
    private static final double GAP = 10;
    private final Parent view;
    private final TextField addressField = new TextField();
    private final TextField portField = new TextField();
    private final Button connectButton = new Button("Connect");
    private final GraphicClient client;

    /**
     * Конструктор класса ConnectionView.
     * @param client клиентская часть графического клиента
     */
    public ConnectionView(GraphicClient client) {
        this.client = client;
        view = createLayout();
    }

    /**
     * Возвращает представление подключения к серверу.
     * @return представление подключения
     */
    public Parent getView() {
        return view;
    }

    /**
     * Создает макет представления подключения к серверу.
     * @return макет представления
     */
    private Parent createLayout() {
        connectButton.textProperty().bind(LocaleManager.getObservableStringByKey("connectButton"));
        final Label addressLabel = new Label();
        addressLabel.textProperty().bind(LocaleManager.getObservableStringByKey("addressLabel"));
        addressField.promptTextProperty().bind(LocaleManager.getObservableStringByKey("addressPrompt"));
        final Label portLabel = new Label();
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
        connectButton.setOnMouseClicked(e -> client.getNetwork().connect(new InetSocketAddress(addressField.getText(), Integer.parseInt(portField.getText()))));

        GridPane connectionLayout = new GridPane();

        connectionLayout.add(addressLabel, 0, 0);
        connectionLayout.add(portLabel, 0, 1);
        connectionLayout.add(addressField, 1, 0);
        connectionLayout.add(portField, 1, 1);
        connectionLayout.setHgap(GAP);
        connectionLayout.setVgap(GAP);
        connectionLayout.setAlignment(Pos.CENTER);
        connectionLayout.add(connectButton, 0, 2, 2, 1);
        connectButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        return connectionLayout;
    }
}
