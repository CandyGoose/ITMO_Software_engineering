package client.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;

import client.ConsoleClient;
import client.GraphicClient;
import client.LocaleManager;
import client.util.LocalizationUtil;
import common.data.Organization;
import common.network.Request;
import common.network.RequestBody;
import common.network.RequestBodyWithOrganization;
import common.network.Response;
import common.network.BasicUserIO;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Класс меню команд.
 */
public class CommandsMenu extends Menu {
    private static final int GAP = 8;
    private final GraphicClient client;

    /**
     * Конструктор класса CommandsMenu.
     * @param client клиентская часть графического клиента
     */
    public CommandsMenu(GraphicClient client) {
        super("Commands");
        textProperty().bind(LocaleManager.getObservableStringByKey("commandsMenuName"));
        this.client = client;

        MenuItem add = new MenuItem();
        MenuItem info = new MenuItem();
        MenuItem printUniqueEmployeesCount = new MenuItem();
        MenuItem descendingAnnualTurnover = new MenuItem();
        MenuItem executeScript = new MenuItem();
        MenuItem clear = new MenuItem();
        MenuItem removeFirst = new MenuItem();
        getItems().addAll(add, info, printUniqueEmployeesCount, descendingAnnualTurnover, clear, removeFirst, executeScript);

        LocalizationUtil.bindTextToLocale(add.textProperty(), "addCommand");
        add.setOnAction(e -> displayAddOrganizationWindow());
        LocalizationUtil.bindTextToLocale(info.textProperty(), "infoCommand");
        info.setOnAction(e -> showCommandResultAsAlert(executeCommandWithEmptyBody("info")));
        LocalizationUtil.bindTextToLocale(printUniqueEmployeesCount.textProperty(), "uniqueEmployeesCountCommand");
        printUniqueEmployeesCount.setOnAction(e -> showCommandResultAsAlert(executeCommandWithEmptyBody("print_unique_employees_count")));
        LocalizationUtil.bindTextToLocale(descendingAnnualTurnover.textProperty(), "descendingAnnualTurnoverCommand");
        descendingAnnualTurnover.setOnAction(e -> showCommandResultAsAlert(executeCommandWithEmptyBody("print_field_descending_annual_turnover ")));
        LocalizationUtil.bindTextToLocale(clear.textProperty(), "clearCommand");
        clear.setOnAction(e -> executeCommandWithEmptyBody("clear"));
        LocalizationUtil.bindTextToLocale(removeFirst.textProperty(), "removeFirstCommand");
        removeFirst.setOnAction(e -> showCommandResultAsAlert(executeCommandWithEmptyBody("remove_first")));
        LocalizationUtil.bindTextToLocale(executeScript.textProperty(),"executeScriptCommand");
        executeScript.setOnAction(e -> chooseScriptAndExecute());
    }

    /**
     * Отображает окно для добавления организации.
     */
    private void displayAddOrganizationWindow() {
        Organization emptyOrganization = new Organization();
        emptyOrganization.setOwner(client.getAuth().getLogin());
        ObjectProperty<Organization> organizationProperty = new SimpleObjectProperty<>(null);
        OrganizationInspectorView inspector = new OrganizationInspectorView(client, organizationProperty);
        organizationProperty.set(emptyOrganization);
        Button addButton = new Button();
        addButton.textProperty().bind(LocaleManager.getObservableStringByKey("addCommand"));
        addButton.disableProperty().bind(Bindings.not(inspector.organizationReadyProperty()));
        VBox stageBox = new VBox();
        stageBox.getChildren().addAll(inspector.getView(), addButton);
        stageBox.setAlignment(Pos.TOP_CENTER);
        stageBox.setPadding(new Insets(GAP));
        stageBox.setSpacing(4*GAP);
        Stage organizationStage = new Stage();
        organizationStage.initOwner(client.getMainWindow());
        organizationStage.initModality(Modality.WINDOW_MODAL);
        organizationStage.titleProperty().bind(LocaleManager.getObservableStringByKey("addCommandTitle"));
        organizationStage.setScene(new Scene(stageBox));
        addButton.setOnMouseClicked(e -> {
            client.getNetwork().sendMessage(new Request(
                    "add",
                    new RequestBodyWithOrganization(new String[] {}, inspector.getOrganization()),
                    client.getAuth()
            ));
            organizationStage.close();
        });
        organizationStage.showAndWait();
    }

    /**
     * Выбирает и выполняет скрипт.
     */
    public void chooseScriptAndExecute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.titleProperty().bind(LocaleManager.getObservableStringByKey("scriptChooserTitle"));
        File selectedFile = fileChooser.showOpenDialog(client.getMainWindow());
        if (selectedFile != null) {
            try (
                    FileInputStream fileInput = new FileInputStream(selectedFile);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()
            ) {
                BasicUserIO scriptIO = new BasicUserIO(fileInput, baos);
                scriptIO.setRepeatInput(true);

                ConsoleClient console = new ConsoleClient(
                        client.getNetwork().channelProperty().get().getSocket().getRemoteAddress(),
                        scriptIO
                );
                console.setAuth(client.getAuth());
                console.run();

                TextArea area = new TextArea();
                area.setEditable(false);
                area.setText(baos.toString());

                Stage resultWindow = new Stage();
                resultWindow.initOwner(client.getMainWindow());
                resultWindow.initModality(Modality.WINDOW_MODAL);
                resultWindow.titleProperty().bind(LocaleManager.getObservableStringByKey("scriptResultTitle"));
                resultWindow.setScene(new Scene(new BorderPane(area)));
                resultWindow.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR, e.getLocalizedMessage());
                alert.setTitle(LocaleManager.getObservableStringByKey("error").get());
                alert.setContentText("");
                alert.showAndWait();
            }
        }
    }

    /**
     * Выполняет команду с пустым телом.
     * @param commandName название команды
     * @return ответ от сервера
     */
    private Response executeCommandWithEmptyBody(String commandName) {
        return client.getNetwork().sendMessage(new Request(
                commandName,
                new RequestBody(new String[] {}),
                client.getAuth())
        );
    }

    /**
     * Отображает результат команды в виде диалогового окна.
     * @param resp ответ от сервера
     */
    private void showCommandResultAsAlert(Response resp) {
        if (resp != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.titleProperty().bind(LocaleManager.getObservableStringByKey("commandResultLabel"));
            alert.headerTextProperty().bind(LocaleManager.getObservableStringByKey("commandSuccessLabel"));
            if (resp.getLocaleKey() == null) {
                alert.setContentText(resp.getMessage());
            } else {
                alert.contentTextProperty().bind(Bindings.createStringBinding(
                        () -> MessageFormat.format(LocaleManager.getObservableStringByKey(resp.getLocaleKey()).get(), resp.getParams()),
                        LocaleManager.localeProperty())
                );
            }
            alert.show();
        }
    }
}
