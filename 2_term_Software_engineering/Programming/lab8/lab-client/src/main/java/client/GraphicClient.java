package client;

import java.io.IOException;
import java.util.*;

import client.util.LocaleManager;
import client.views.CommandsMenu;
import client.views.ConnectionController;
import client.views.LoginController;
import client.views.MainView;
import common.data.Organization;
import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import common.network.ResponseWithOrganizations;
import common.util.AuthCredentials;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Класс GraphicClient представляет графический клиент приложения.
 * Он обеспечивает инициализацию, управление окнами и визуальным интерфейсом приложения,
 * а также обработку сетевых операций и взаимодействие с сервером.
 */
public class GraphicClient extends Application {

    /**
     * Время в миллисекундах
     */
    private static final long SLEEP_TIME = 150;

    /**
     * Таймер
     */
    private final Timer organizationsTimer = new Timer("organizations-fetch-thread", true);

    /**
     * Получение организаций
     */
    private final TimerTask organizationFetcher = new TimerTask() {
        @Override
        public void run() {
            if (doOrganizationFetch) {
                Response resp = network.sendMessage(new Request(
                        "show",
                        new RequestBody(new String[] {}),
                        getAuth()
                ));

                if (resp instanceof ResponseWithOrganizations) {
                    ResponseWithOrganizations rwr = (ResponseWithOrganizations) resp;
                    Set<Organization> newOrganizations = new HashSet<>(Arrays.asList(rwr.getOrganizations()));

                    Platform.runLater(() -> setOrganizations(newOrganizations));
                }
            }
        }
    };

    /**
     * Флаг, указывающий на необходимость получения списка организаций.
     */
    private volatile boolean doOrganizationFetch = false;

    /**
     * Объект для сетевого взаимодействия.
     */
    private final GraphicClientNet network = new GraphicClientNet();

    /**
     * Объект, представляющий аутентификационные данные клиента.
     */
    private final ObjectProperty<AuthCredentials> auth = new SimpleObjectProperty<>();

    /**
     * Множество организаций
     */
    private final ObservableSet<Organization> organizations = FXCollections.observableSet();

    /**
     * Визуальные представления различных экранов приложения.
     */
    private final ConnectionController connectionController = new ConnectionController(this);
    private final LoginController loginController = new LoginController(this);
    private final MainView mainView = new MainView(this);

    /**
     * Меню приложения для выбора языка и доступных команд.
     */
    @FXML
    private final Menu languageMenu = new Menu("Language");
    private final Menu commandsMenu = new CommandsMenu(this);

    /**
     * Панель меню приложения.
     */
    @FXML
    private final MenuBar menuBar = new MenuBar(languageMenu);

    /**
     * Корневой контейнер сцены.
     */
    @FXML
    private final BorderPane sceneRoot = new BorderPane();

    /**
     * Сцена приложения.
     */
    private final Scene scene = new Scene(sceneRoot);

    /**
     * Основное окно приложения.
     */
    private Stage mainWindow;
    @FXML
    private RadioMenuItem englishMenuItem;
    @FXML
    private RadioMenuItem russianMenuItem;
    @FXML
    private RadioMenuItem romanianMenuItem;
    @FXML
    private RadioMenuItem hungarianMenuItem;

    public GraphicClient() throws IOException {
    }

    /**
     * Метод инициализации приложения.
     * Устанавливает привязку текста языкового меню к соответствующему ключу в файле локализации.
     * Создает элементы меню для выбора языка и устанавливает обработчики событий для каждого элемента.
     * Группирует элементы меню в одну группу переключателей и устанавливает выбранный элемент по умолчанию.
     * Добавляет элементы меню в языковое меню.
     */
    @FXML
    public void initialize() {

        languageMenu.textProperty().bind(LocaleManager.getObservableStringByKey("languageMenuName"));
        englishMenuItem.setOnAction(e -> LocaleManager.setLocale(Locale.forLanguageTag("en-IN")));
        russianMenuItem.setOnAction(e -> LocaleManager.setLocale(Locale.forLanguageTag("ru")));
        romanianMenuItem.setOnAction(e -> LocaleManager.setLocale(Locale.forLanguageTag("ro")));
        hungarianMenuItem.setOnAction(e -> LocaleManager.setLocale(new Locale("hu")));
        ToggleGroup group = new ToggleGroup();
        englishMenuItem.setToggleGroup(group);
        russianMenuItem.setToggleGroup(group);
        romanianMenuItem.setToggleGroup(group);
        hungarianMenuItem.setToggleGroup(group);
        englishMenuItem.setSelected(true);
        languageMenu.getItems().addAll(englishMenuItem, russianMenuItem, romanianMenuItem, hungarianMenuItem);
    }

    /**
     * Метод, вызываемый при запуске приложения.
     * Задает основное окно приложения (primaryStage).
     * Запускает таймер organizationsTimer для периодического обновления списка организаций с сервера.
     * Устанавливает привязку заголовка основного окна к соответствующему ключу в файле локализации.
     * Определяет размеры основного окна на основе размеров экрана.
     * Устанавливает содержимое сцены с использованием корневого контейнера sceneRoot.
     * Устанавливает свойства основного окна, такие как полноэкранный режим и максимизация.
     * Отображает основное окно.
     * Устанавливает слушатель изменений свойства channelProperty в объекте network.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));

        mainWindow = primaryStage;

        organizationsTimer.schedule(organizationFetcher, 0L, SLEEP_TIME);

        primaryStage.titleProperty().bind(LocaleManager.getObservableStringByKey("loginHeader"));

        double screenWidth = Screen.getPrimary().getBounds().getWidth() * 0.8;
        double screenHeight = Screen.getPrimary().getBounds().getHeight() * 0.8;
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);

        sceneRoot.setTop(menuBar);
        sceneRoot.setCenter(connectionController.getView());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(true);

        primaryStage.show();

        network.channelProperty().addListener((o, oldVal, newVal) -> {
            if (newVal == null) {
                setAuth(null);
                sceneRoot.setCenter(connectionController.getView());
            } else {
                sceneRoot.setCenter(loginController.getView());
            }
        });
    }

    /**
     * Метод, вызываемый при завершении приложения.
     * Закрывает сокет в объекте network.
     * Игнорирует возможное исключение IOException.
     */
    @Override
    public void stop() {
        try {
            network.closeSocket();
        } catch (IOException ignore) {
        }
    }

    /**
     * Метод для получения объекта GraphicClientNet, используемого для сетевого взаимодействия.
     * Возвращает объект GraphicClientNet.
     */
    public GraphicClientNet getNetwork() {
        return network;
    }

    /**
     * Метод для получения основного окна приложения.
     * Возвращает объект Stage основного окна.
     */
    public Stage getMainWindow() {
        return mainWindow;
    }

    /**
     * Метод для получения свойства authProperty типа ObjectProperty<AuthCredentials>.
     * Возвращает свойство authProperty.
     */
    public ObjectProperty<AuthCredentials> authProperty() {
        return auth;
    }

    /**
     * Метод для получения объекта AuthCredentials, представляющего аутентификационные данные клиента.
     * Возвращает объект AuthCredentials.
     */
    public AuthCredentials getAuth() {
        return auth.get();
    }

    /**
     * Метод для установки аутентификационных данных клиента.
     * Если аутентификационные данные равны null, устанавливает флаг doOrganizationFetch в false,
     * устанавливает содержимое центральной области sceneRoot на экран входа loginView,
     * и удаляет меню commandsMenu из панели меню menuBar.
     * В противном случае устанавливает флаг doOrganizationFetch в true,
     * устанавливает содержимое центральной области sceneRoot на основной экран mainView,
     * и добавляет меню commandsMenu в панель меню menuBar.
     * Устанавливает новые аутентификационные данные в свойство authProperty.
     */
    public void setAuth(AuthCredentials auth) {
        if (auth == null) {
            doOrganizationFetch = false;
            sceneRoot.setCenter(loginController.getView());
            menuBar.getMenus().remove(commandsMenu);
        } else {
            doOrganizationFetch = true;
            sceneRoot.setCenter(mainView.getView());
            menuBar.getMenus().add(commandsMenu);
        }
        this.auth.set(auth);
    }

    /**
     * Метод для получения наблюдаемого множества организаций.
     * Возвращает наблюдаемое множество organizations.
     */
    public ObservableSet<Organization> organizationsProperty() {
        return organizations;
    }

    /**
     * Метод для получения множества организаций.
     * Преобразует наблюдаемое множество organizations в обычное множество и возвращает его.
     */
    public Set<Organization> getOrganizations() {
        return new HashSet<>(organizations);
    }

    /**
     * Метод для установки списка организаций.
     * Сохраняет только те организации, которые уже находятся в наблюдаемом множестве organizations,
     * а затем добавляет новые организации из переданной коллекции в наблюдаемое множество organizations.
     */
    public void setOrganizations(Collection<Organization> organizations) {
        this.organizations.retainAll(organizations);
        this.organizations.addAll(organizations);
    }
}