package client.views;

import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;

import client.GraphicClient;
import client.util.LocaleManager;
import common.data.Organization;
import common.network.Request;
import common.network.RequestBody;
import common.network.RequestBodyWithOrganization;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Главное представление клиента.
 */
public class MainView {
    private static final int GAP = 20;
    private static final int SMALL_GAP = 7;
    private Parent view;
    private GraphicClient client;
    private OrganizationsTableView tableTab;
    private OrganizationsGraphicView graphicTab;
    private FilterView filterView;
    private OrganizationInspectorView inspector;
    private ObservableSet<Organization> filteredOrganizations = FXCollections.observableSet();
    private final InvalidationListener listener = new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            Set<Organization> newFilteredOrganizations = client.getOrganizations().stream().filter(filterView.getFilter()).collect(Collectors.toSet());
            filteredOrganizations.retainAll(newFilteredOrganizations);
            filteredOrganizations.addAll(newFilteredOrganizations);
        }
    };

    /**
     * Создает экземпляр главного представления клиента.
     *
     * @param client клиент
     */
    public MainView(GraphicClient client) {
        this.client = client;
        filterView = new FilterView();
        filterView.filterProperty().addListener(listener);
        client.organizationsProperty().addListener(listener);

        BorderPane root = new BorderPane();
        root.setLeft(filterView.getView());
        tableTab = new OrganizationsTableView(filteredOrganizations);
        graphicTab = new OrganizationsGraphicView(filteredOrganizations);
        tableTab.selectedOrganizationProperty().bindBidirectional(graphicTab.selectedOrganizationProperty());
        Tab tab1 = new Tab("Organizations (Table)", tableTab.getView());
        tab1.textProperty().bind(LocaleManager.getObservableStringByKey("organizationsTable"));
        Tab tab2 = new Tab("Graph View", graphicTab.getView());
        tab2.textProperty().bind(LocaleManager.getObservableStringByKey("organizationsGraph"));
        TabPane center = new TabPane(tab1, tab2);
        center.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        root.setCenter(center);
        root.setTop(createUserInfoBox());
        root.setRight(createOrganizationInspectionBox());

        this.view = root;
    }

    /**
     * Возвращает представление главного окна.
     *
     * @return представление главного окна
     */
    public Parent getView() {
        return view;
    }

    /**
     * Метод для отправки обновленной организации.
     * @param e событие мыши
     */
    private void sendUpdatedOrganization(MouseEvent e) {
        client.getNetwork().sendMessage(new Request(
            "update",
            new RequestBodyWithOrganization(new String[] {}, inspector.getOrganization()),
            client.getAuth()
        ));
    }

    /**
     * Метод для удаления выбранной организации.
     * @param e событие мыши
     */
    private void deleteSelectedOrganization(MouseEvent e) {
        client.getNetwork().sendMessage(new Request(
            "remove_by_id",
            new RequestBody(new String[] {Long.toString(inspector.getOrganization().getId())}),
            client.getAuth()
        ));
    }

    /**
     * Создает блок для просмотра информации об организации.
     * @return созданный блок с информацией об организации
     */
    private Node createOrganizationInspectionBox() {
        inspector = new OrganizationInspectorView(client, tableTab.selectedOrganizationProperty());
        Button updateButton = new Button();
        updateButton.textProperty().bind(LocaleManager.getObservableStringByKey("updateButton"));
        updateButton.disableProperty().bind(Bindings.not(inspector.organizationReadyProperty()));
        updateButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(updateButton, Priority.ALWAYS);
        updateButton.setOnMouseClicked(this::sendUpdatedOrganization);
        Button deleteButton = new Button("Delete");
        deleteButton.textProperty().bind(LocaleManager.getObservableStringByKey("deleteButton"));
        deleteButton.disableProperty().bind(Bindings.not(inspector.organizationReadyProperty()));
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(deleteButton, Priority.ALWAYS);
        deleteButton.setOnMouseClicked(this::deleteSelectedOrganization);
        HBox buttonBox = new HBox(SMALL_GAP);
        buttonBox.setPadding(new Insets(0, SMALL_GAP, 0, SMALL_GAP));
        buttonBox.setMaxWidth(Double.MAX_VALUE);
        buttonBox.getChildren().addAll(updateButton, deleteButton);
        VBox organizationInspectionBox = new VBox(GAP);
        organizationInspectionBox.getChildren().addAll(inspector.getView(), buttonBox);

        return organizationInspectionBox;
    }

    /**
     * Создает блок с информацией о пользователе.
     * @return созданный блок с информацией о пользователе
     */
    private Node createUserInfoBox() {
        Label usernameLabel = new Label();
        usernameLabel.textProperty().bind(Bindings.createStringBinding(
            () -> client.getAuth() == null ? "" : MessageFormat.format(LocaleManager.getObservableStringByKey("loggedInAsLabel").get(), client.getAuth().getLogin()),
            LocaleManager.localeProperty(),
            client.authProperty()
        ));
        Button logoutButton = new Button();
        logoutButton.textProperty().bind(LocaleManager.getObservableStringByKey("logoutButton"));
        logoutButton.setOnMouseClicked(e -> client.setAuth(null));
        HBox userInfo = new HBox(GAP);
        userInfo.setAlignment(Pos.CENTER_RIGHT);
        userInfo.setPadding(new Insets(GAP));
        userInfo.getChildren().addAll(usernameLabel, logoutButton);

        Label selectedLabel = new Label();
        userInfo.getChildren().add(selectedLabel);
        selectedLabel.textProperty().bind(Bindings.createStringBinding(
            () -> {
                Object idOrNoneLabel = (tableTab.selectedOrganizationProperty().get() == null ? LocaleManager.getObservableStringByKey("noneLabel").get() : tableTab.selectedOrganizationProperty().get().getId());
                return MessageFormat.format(LocaleManager.getObservableStringByKey("selectedLabel").get(), idOrNoneLabel);
            },
            tableTab.selectedOrganizationProperty(),
            LocaleManager.localeProperty()
        ));

        return userInfo;
    }
}
