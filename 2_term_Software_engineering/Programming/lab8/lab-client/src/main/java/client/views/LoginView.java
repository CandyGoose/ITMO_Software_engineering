package client.views;

import java.io.IOException;
import java.text.MessageFormat;

import client.GraphicClient;
import client.LocaleManager;
import client.util.LocalizationUtil;
import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import common.network.ResponseWithAuthCredentials;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginView {
    private final Parent view;
    private GraphicClient client;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private Label headerLabel;
    @FXML
    private Label subheaderLabel;
    @FXML
    private Label prompt;
    @FXML
    private Label loginLabel;
    @FXML
    private Label passwordLabel;
    private StringProperty promptMsg = new SimpleStringProperty("");

    public LoginView(GraphicClient client) throws IOException {
        this.client = client;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
        fxmlLoader.setController(this);
        view = fxmlLoader.load();
        initialize();
    }

    public Parent getView() {
        return view;
    }

    private void disableButtons() {
        loginButton.setDisable(true);
        registerButton.setDisable(true);
        disconnectButton.setDisable(true);
    }

    private void enableButtons() {
        loginButton.setDisable(false);
        registerButton.setDisable(false);
        disconnectButton.setDisable(false);
    }

    @FXML
    private void sendLoginRequest(ActionEvent event) {
        event.consume();
        sendRequest("login");
    }

    @FXML
    private void sendRegisterRequest(ActionEvent event) {
        event.consume();
        sendRequest("register");
    }

    private void sendRequest(String requestType) {
        disableButtons();
        promptMsg.unbind();
        promptMsg.set("");
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {
            promptMsg.bind(LocaleManager.getObservableStringByKey("loginNotNull"));
            enableButtons();
            return;
        }

        Response resp = client.getNetwork().sendMessage(new Request(requestType, new RequestBody(new String[]{login, password}),null));

        if (resp != null) {
            if (resp instanceof ResponseWithAuthCredentials) {
                client.setAuth(((ResponseWithAuthCredentials) resp).getAuthCredentials());
            } else {
                if (resp.getLocaleKey() == null) {
                    promptMsg.set(resp.getMessage());
                } else {
                    promptMsg.bind(Bindings.createStringBinding(
                            () -> MessageFormat.format(LocaleManager.getObservableStringByKey(resp.getLocaleKey()).get(), resp.getParams()),
                            LocaleManager.localeProperty())
                    );
                }
            }
        }
        enableButtons();
    }


    @FXML
    private void initialize() {
        LocalizationUtil.bindTextToLocale(headerLabel.textProperty(), "loginHeader");
        LocalizationUtil.bindTextToLocale(subheaderLabel.textProperty(), "loginSubHeader");
        LocalizationUtil.bindTextToLocale(loginButton.textProperty(), "loginButton");
        LocalizationUtil.bindTextToLocale(registerButton.textProperty(), "registerButton");
        LocalizationUtil.bindTextToLocale(disconnectButton.textProperty(), "disconnectButton");
        LocalizationUtil.bindTextToLocale(loginLabel.textProperty(), "loginLabel");
        LocalizationUtil.bindTextToLocale(loginField.promptTextProperty(), "loginPrompt");
        LocalizationUtil.bindTextToLocale(passwordLabel.textProperty(), "passwordLabel");
        LocalizationUtil.bindTextToLocale(passwordField.promptTextProperty(), "passwordPrompt");
        disconnectButton.setOnMouseClicked(e -> client.getNetwork().disconnect());
        prompt.textProperty().bind(promptMsg);
    }
}
