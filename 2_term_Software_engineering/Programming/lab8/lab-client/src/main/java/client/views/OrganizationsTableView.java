package client.views;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import client.LocaleManager;
import common.data.Organization;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.ScrollPane;

/**
 * Класс представления таблицы организаций.
 */
public class OrganizationsTableView {
    private ScrollPane scrollPane;
    private TableView<Organization> tableView;
    private ObservableList<Organization> orderedOrganizationList = FXCollections.observableList(new LinkedList<>());
    private ObjectProperty<Organization> selectedOrganizationProperty = new SimpleObjectProperty<>(null);

    /**
     * Конструктор класса OrganizationsTableView.
     * @param organizations набор организаций
     */
    public OrganizationsTableView(ObservableSet<Organization> organizations) {
        tableView = createTable();
        scrollPane = new ScrollPane(tableView);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        organizations.addListener((SetChangeListener<Organization>) change -> {
            if (change.wasAdded()) {
                for (int i = 0; i < orderedOrganizationList.size(); i++) {
                    if (getComparator().compare(change.getElementAdded(), orderedOrganizationList.get(i)) < 0) {
                        orderedOrganizationList.add(i, change.getElementAdded());
                        return;
                    }
                }
                orderedOrganizationList.add(change.getElementAdded());
            }
            if (change.wasRemoved()) {
                if (tableView.getSelectionModel().getSelectedItem() == change.getElementRemoved()) {
                    tableView.getSelectionModel().select(null);
                }
                orderedOrganizationList.remove(change.getElementRemoved());
            }
        });

        tableView.setOnSort(e -> {
            e.consume();
            sortList();
        });
        selectedOrganizationProperty.addListener((o, oldVal, newVal) -> {
            tableView.getSelectionModel().select(newVal);
        });
        tableView.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            setSelectedOrganization(newVal);
        });
    }

    /**
     * Получить представление таблицы.
     * @return представление таблицы
     */
    public Parent getView() {
        return scrollPane;
    }

    /**
     * Получить свойство выбранной организации.
     * @return свойство выбранной организации
     */
    public ObjectProperty<Organization> selectedOrganizationProperty() {
        return selectedOrganizationProperty;
    }

    /**
     * Получить выбранную организацию.
     * @return выбранная организация
     */
    public Organization getSelectedOrganization() {
        return selectedOrganizationProperty.get();
    }

    /**
     * Задать выбранную организацию.
     * @param organization выбранная организация
     */
    public void setSelectedOrganization(Organization organization) {
        selectedOrganizationProperty.set(organization);
        if (organization != tableView.getSelectionModel().getSelectedItem()) {
            tableView.getSelectionModel().select(organization);
        }
    }

    /**
     * Возвращает компаратор для сортировки списка организаций.
     *
     * @return компаратор для сортировки списка организаций
     */
    private Comparator<Organization> getComparator() {
        return (tableView.getComparator() == null ? Organization::compareTo : tableView.getComparator());
    }

    /**
     * Сортирует список организаций.
     */
    private void sortList() {
        Iterator<Organization> it = orderedOrganizationList.stream().sorted(getComparator()).iterator();
        Map<Organization, Integer> permMap = IntStream.range(0, orderedOrganizationList.size())
                                               .boxed().collect(Collectors.toMap(x -> it.next(), x -> x));
        orderedOrganizationList.sort(Comparator.comparing(permMap::get));
    }

    /**
     * Создает таблицу для отображения организаций.
     *
     * @return таблица для отображения организаций
     */
    private TableView<Organization> createTable() {
        TableColumn<Organization, Long> idCol = createColumn("idLabel", new PropertyValueFactory<>("id"),
            id -> NumberFormat.getIntegerInstance().format(id));
        TableColumn<Organization, String> nameCol = createColumn("nameLabel", new PropertyValueFactory<>("name"),
            x -> x);
        TableColumn<Organization, LocalDateTime> creationDateCol = createColumn("creationDateLabel", new PropertyValueFactory<>("creationDate"),
            date -> date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        TableColumn<Organization, Float> annualTurnoverCol = createColumn("annualTurnoverLabel", new PropertyValueFactory<>("annualTurnover"),
            annualTurnover -> NumberFormat.getNumberInstance().format(annualTurnover));
        TableColumn<Organization, String> fullNameCol = createColumn("fullNameLabel", new PropertyValueFactory<>("fullName"),
            x -> x);
        TableColumn<Organization, Long> employeesCountCol = createColumn("employeesCountLabel", new PropertyValueFactory<>("employeesCount"),
            employeesCount -> NumberFormat.getNumberInstance().format(employeesCount));
        TableColumn<Organization, Float> xCol = createColumn("xLabel", data -> new SimpleObjectProperty<>(data.getValue().getCoordinates().getX()),
            x -> NumberFormat.getIntegerInstance().format(x));
        TableColumn<Organization, Float> yCol = createColumn("yLabel", data -> new SimpleObjectProperty<>(data.getValue().getCoordinates().getY()),
            x -> NumberFormat.getNumberInstance().format(x));
        TableColumn<Organization, String> streetCol = createColumn("streetLabel", data -> new SimpleStringProperty(data.getValue().getAddress().getStreet()),
            x -> x);
        TableColumn<Organization, String> typeCol = createColumn("typeLabel", data -> new SimpleStringProperty(data.getValue().getType().name()),
            x -> x);
        TableColumn<Organization, String> ownerCol = createColumn("ownerLabel", new PropertyValueFactory<>("owner"),
            x -> x);
        TableView<Organization> table = new TableView<>(orderedOrganizationList);
        table.getColumns().addAll(idCol, nameCol, creationDateCol, annualTurnoverCol, fullNameCol, employeesCountCol, xCol, yCol, streetCol, typeCol, ownerCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    /**
     * Создает столбец таблицы с заданными свойствами и форматтером.
     *
     * @param localeKey ключ локализованной строки для заголовка столбца
     * @param valueFactory фабрика значений для столбца
     * @param formatter форматтер для отображения значений столбца
     * @param <T> тип значений столбца
     * @return столбец таблицы
     */
    private <T> TableColumn<Organization, T> createColumn(String localeKey, Callback<CellDataFeatures<Organization, T>, ObservableValue<T>> valueFactory, Callback<T, String> formatter) {
        TableColumn<Organization, T> newColumn = new TableColumn<>();
        newColumn.textProperty().bind(LocaleManager.getObservableStringByKey(localeKey));
        newColumn.setCellValueFactory(valueFactory);
        newColumn.setCellFactory(tc -> new LocaleFormattedTableCell<>(formatter));
        return newColumn;
    }

    /**
     * Ячейка таблицы, отображающая отформатированные значения с учетом локализации.
     *
     * @param <T> тип значения ячейки
     */
    private class LocaleFormattedTableCell<T> extends TableCell<Organization, T> {
        /**
         * Тип значения ячейки
         */
        private Callback<T, String> formatter;

        /**
         * Ячейка таблицы, отображающая отформатированные значения с учетом локализации.
         *
         * @param formatter тип значения ячейки
         */
        LocaleFormattedTableCell(Callback<T, String> formatter) {
            this.formatter = formatter;
            setAlignment(Pos.CENTER);
        }


        /**
         * Обновляет содержимое ячейки с учетом переданного значения и флага пустоты.
         * @param item значение элемента ячейки
         * @param empty флаг, указывающий на пустоту ячейки
         */
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                textProperty().unbind();
                setText("");
            } else {
                textProperty().bind(Bindings.createStringBinding(() -> formatter.call(item), LocaleManager.localeProperty()));
            }
        }
    }
}
