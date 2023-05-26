package client.views;

import java.io.IOException;
import java.net.InetSocketAddress;

import client.GraphicClient;

import client.util.LocalizationUtil;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class ConnectionView {
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

    public ConnectionView(GraphicClient client) throws IOException {
        this.client = client;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ConnectionView.fxml"));
        fxmlLoader.setController(this);
        view = fxmlLoader.load();
        initialize();
    }

    public Parent getView() {
        return view;
    }

    @FXML
    private void initialize() {

        LocalizationUtil.bindTextToLocale(connectButton.textProperty(), "connectButton");
        LocalizationUtil.bindTextToLocale(addressLabel.textProperty(), "addressLabel");
        LocalizationUtil.bindTextToLocale(addressField.promptTextProperty(), "addressPrompt");
        LocalizationUtil.bindTextToLocale(portLabel.textProperty(), "portLabel");
        LocalizationUtil.bindTextToLocale(portField.promptTextProperty(), "portPrompt");

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
