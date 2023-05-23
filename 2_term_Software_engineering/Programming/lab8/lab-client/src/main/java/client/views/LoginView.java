package client.views;

import java.text.MessageFormat;

import client.GraphicClient;
import client.LocaleManager;
import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import common.network.ResponseWithAuthCredentials;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Класс представления для экрана входа в систему.
 */
public class LoginView {
    private static final double GAP = 10;
    private static final double HEADER_FONT_SIZE = 50;
    private Node view;
    private GraphicClient client;
    private TextField loginField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Button disconnectButton;
    private StringProperty promptMsg = new SimpleStringProperty("");

    /**
     * Конструктор класса LoginView.
     *
     * @param client экземпляр GraphicClient
     */
    public LoginView(GraphicClient client) {
        this.client = client;
        view = createLayout();
    }

    /**
     * Возвращает узел представления.
     *
     * @return узел представления
     */
    public Node getView() {
        return view;
    }

    /**
     * Деактивировать кнопки
     */
    private void disableButtons() {
        loginButton.setDisable(true);
        registerButton.setDisable(true);
        disconnectButton.setDisable(true);
    }

    /**
     * Активировать кнопки
     */
    private void enableButtons() {
        loginButton.setDisable(false);
        registerButton.setDisable(false);
        disconnectButton.setDisable(false);
    }

    /**
     * Отправляет запрос на вход в систему при щелчке на кнопке входа.
     *
     * @param event событие мыши
     */
    private void sendLoginRequest(MouseEvent event) {
        event.consume();
        disableButtons();
        promptMsg.unbind();
        promptMsg.set("");
        Response resp = client.getNetwork().sendMessage(new Request(
            "login",
            new RequestBody(new String[] {loginField.getText(), passwordField.getText()}),
            null
        ));

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

    /**
     * Отправляет запрос на регистрацию при щелчке на кнопке регистрации.
     *
     * @param event событие мыши
     */
    private void sendRegisterRequest(MouseEvent event) {
        event.consume();
        disableButtons();
        promptMsg.unbind();
        promptMsg.set("");
        Response resp = client.getNetwork().sendMessage(new Request(
            "register",
            new RequestBody(new String[] {loginField.getText(), passwordField.getText()}),
            null
        ));

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

    /**
     * Создает макет экрана входа в систему.
     *
     * @return узел, содержащий макет экрана входа в систему
     */
    private Node createLayout() {
        final Label headerLabel = new Label();
        headerLabel.textProperty().bind(LocaleManager.getObservableStringByKey("loginHeader"));
        headerLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, HEADER_FONT_SIZE));
        final Label subheaderLabel = new Label();
        subheaderLabel.textProperty().bind(LocaleManager.getObservableStringByKey("loginSubHeader"));

        loginButton = new Button();
        loginButton.textProperty().bind(LocaleManager.getObservableStringByKey("loginButton"));
        loginButton.setOnMouseClicked(this::sendLoginRequest);
        registerButton = new Button();
        registerButton.textProperty().bind(LocaleManager.getObservableStringByKey("registerButton"));
        registerButton.setOnMouseClicked(this::sendRegisterRequest);
        disconnectButton = new Button();
        disconnectButton.textProperty().bind(LocaleManager.getObservableStringByKey("disconnectButton"));
        disconnectButton.setOnMouseClicked(e -> client.getNetwork().disconnect());
        HBox buttonGroup = new HBox(GAP);
        buttonGroup.getChildren().addAll(loginButton, registerButton, disconnectButton);
        buttonGroup.setAlignment(Pos.CENTER);

        Label prompt = new Label();
        prompt.textProperty().bind(promptMsg);
        prompt.setTextFill(Color.RED);

        VBox box = new VBox(GAP);
        box.getChildren().addAll(headerLabel, subheaderLabel, createLoginPassGrid(), prompt, buttonGroup);
        box.setAlignment(Pos.CENTER);

        return box;
    }

    /**
     * Создает сетку для ввода логина и пароля.
     *
     * @return узел, содержащий сетку для ввода логина и пароля
     */
    private Node createLoginPassGrid() {
        final Label loginLabel = new Label();
        loginLabel.textProperty().bind(LocaleManager.getObservableStringByKey("loginLabel"));
        loginField = new TextField();
        loginField.promptTextProperty().bind(LocaleManager.getObservableStringByKey("loginPrompt"));
        final Label passwordLabel = new Label();
        passwordLabel.textProperty().bind(LocaleManager.getObservableStringByKey("passwordLabel"));
        passwordField = new PasswordField();
        passwordField.promptTextProperty().bind(LocaleManager.getObservableStringByKey("passwordPrompt"));

        GridPane loginPassGrid = new GridPane();
        loginPassGrid.add(loginLabel, 0, 0);
        loginPassGrid.add(passwordLabel, 0, 1);
        loginPassGrid.add(loginField, 1, 0);
        loginPassGrid.add(passwordField, 1, 1);
        loginPassGrid.setHgap(GAP);
        loginPassGrid.setVgap(GAP);
        loginPassGrid.setAlignment(Pos.CENTER);

        return loginPassGrid;
    }
}
