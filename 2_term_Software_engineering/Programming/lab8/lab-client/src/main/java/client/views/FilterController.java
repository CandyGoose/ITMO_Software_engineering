package client.views;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import client.util.LocaleManager;
import client.util.NumberStringConverter;
import client.util.StringConverter;
import common.data.Organization;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * Представление фильтра для организаций.
 */
public class FilterController {
    @FXML
    public VBox filterBox;
    @FXML
    private Label headerLabel;
    @FXML
    private ChoiceBox<FilterConfigurator> filterFieldChoice;

    private static final int GAP = 10;
    private final List<FilterConfigurator> configurators = new LinkedList<>();
    private final ObjectProperty<Predicate<Organization>> filterProperty = new SimpleObjectProperty<>();
    private final ChangeListener<Predicate<Organization>> filterChangeListener = (o, oldVal, newVal) -> filterProperty.set(newVal);
    private Parent view;

    @FXML
    public void initialize() {
        headerLabel.textProperty().bind(LocaleManager.getObservableStringByKey("filterLabel"));

        filterFieldChoice.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            if (oldValue != null) {
                filterBox.getChildren().remove(oldValue.getView());
                oldValue.filterProperty().removeListener(filterChangeListener);
            }
            if (newValue != null) {
                filterBox.getChildren().add(newValue.getView());
                filterProperty.set(newValue.filterProperty.get());
                newValue.filterProperty().addListener(filterChangeListener);
            }
        });
        filterFieldChoice.getSelectionModel().select(0);
        this.view = filterBox;
    }

    /**
     * Возвращает представление фильтра.
     *
     * @return представление фильтра
     */
    public Parent getView() {
        return view;
    }

    /**
     * Возвращает фильтр организаций.
     *
     * @return фильтр организаций
     */
    public Predicate<Organization> getFilter() {
        return filterProperty.get();
    }

    /**
     * Устанавливает фильтр организаций.
     *
     * @param filter фильтр организаций
     */
    public void setFilter(Predicate<Organization> filter) {
        filterProperty.set(filter);
    }


    /**
     * Возвращает свойство фильтра организаций.
     *
     * @return свойство фильтра организаций
     */
    public ObjectProperty<Predicate<Organization>> filterProperty() {
        return filterProperty;
    }

    /**
     * Метод создает и возвращает ChoiceBox с настройками фильтра.
     * @return ChoiceBox с настройками фильтра
     */
    private ChoiceBox<FilterConfigurator> createChoiceBox() {
        configurators.add(new FilterConfigurator("noneLabel"));
        configurators.add(new ComparableFilterConfigurator<Long>("idLabel", x -> x.getId(), new NumberStringConverter<>(Long::parseLong)));
        configurators.add(new StringFilterConfigurator("nameLabel", x -> x.getName()));
        configurators.add(new ComparableFilterConfigurator<Float>("annualTurnoverLabel", x -> x.getAnnualTurnover(), new NumberStringConverter<>(Float::parseFloat)));
        configurators.add(new StringFilterConfigurator("fullNameLabel", x -> x.getFullName()));
        configurators.add(new ComparableFilterConfigurator<Long>("employeesCountLabel", x -> x.getEmployeesCount(), new NumberStringConverter<>(Long::parseLong)));

        configurators.add(new DateFilterConfigurator("creationDateLabel"));
        configurators.add(new ComparableFilterConfigurator<Float>("xLabel", x -> x.getCoordinates().getX(), new NumberStringConverter<>(Float::parseFloat)));
        configurators.add(new ComparableFilterConfigurator<Float>("yLabel", x -> x.getCoordinates().getY(), new NumberStringConverter<>(Float::parseFloat)));
        configurators.add(new StringFilterConfigurator("typeLabel", x -> x.getType().name()));
        configurators.add(new StringFilterConfigurator("streetLabel", x -> x.getAddress().getStreet()));
        configurators.add(new StringFilterConfigurator("ownerLabel", x -> x.getOwner()));
        filterFieldChoice.getItems().addAll(configurators);

        LocaleManager.localeProperty().addListener((o, oldV, newV) -> {
            FilterConfigurator prevChoice = filterFieldChoice.getValue();
            filterFieldChoice.getItems().clear();
            filterFieldChoice.getItems().addAll(configurators);
            filterFieldChoice.getSelectionModel().select(prevChoice);
        });

        return filterFieldChoice;
    }

    /**
     * Вспомогательный класс для настройки фильтра.
     */
    private class FilterConfigurator {
        private Node view;
        private ObjectProperty<Predicate<Organization>> filterProperty = new SimpleObjectProperty<>(x -> true);
        private String localeKey;

        /**
         * Конструктор класса FilterConfigurator.
         * @param localeKey ключ для локализации
         */
        FilterConfigurator(String localeKey) {
            this.localeKey = localeKey;
            Label noFilterLabel = new Label();
            noFilterLabel.textProperty().bind(LocaleManager.getObservableStringByKey("noFilterLabel"));
            setView(noFilterLabel);
        }

        /**
         * Возвращает представление настройки фильтра.
         * @return представление настройки фильтра
         */
        Node getView() {
            return view;
        }

        /**
         * Устанавливает представление настройки фильтра.
         * @param view представление настройки фильтра
         */
        void setView(Node view) {
            this.view = view;
        }

        /**
         * Возвращает свойство фильтра для организации.
         * @return свойство фильтра для организации
         */
        ObjectProperty<Predicate<Organization>> filterProperty() {
            return filterProperty;
        }

        /**
         * Возвращает строковое представление настройки фильтра.
         * @return строковое представление настройки фильтра
         */
        @Override
        public String toString() {
            return LocaleManager.getObservableStringByKey(localeKey).get();
        }
    }

    /**
     * Вспомогательный класс для настройки сравнимого фильтра.
     * @param <T> тип данных
     */
    private class ComparableFilterConfigurator<T extends Comparable<T>> extends FilterConfigurator {
        private ChoiceBox<Operation> operationChoice;
        private TextField operandField;
        private Label errorPrompt;
        private Callback<Organization, T> valueGetter;
        private StringConverter<T> converter;

        /**
         * Конструктор класса ComparableFilterConfigurator.
         * @param localeKey ключ для локализации
         * @param valueGetter функция для получения значения из организации
         * @param converter конвертер для преобразования строки в тип T
         */
        ComparableFilterConfigurator(String localeKey, Callback<Organization, T> valueGetter, StringConverter<T> converter) {
            super(localeKey);
            this.valueGetter = valueGetter;
            this.converter = converter;
            operationChoice = new ChoiceBox<>();
            operationChoice.getItems().addAll(Operation.values());
            operationChoice.getSelectionModel().select(0);
            operandField = new TextField();
            operandField.promptTextProperty().bind(LocaleManager.getObservableStringByKey("operandLabel"));
            errorPrompt = new Label();
            errorPrompt.setTextFill(Color.RED);

            VBox box = new VBox(GAP);
            box.getChildren().addAll(operationChoice, operandField, errorPrompt);
            setView(box);

            operationChoice.getSelectionModel().selectedItemProperty().addListener(x -> {
                filterProperty().set(createFilter());
            });
            operandField.textProperty().addListener(x -> filterProperty().set(createFilter()));
        }

        /**
         * Создает фильтр для организации на основе выбранных настроек.
         * @return фильтр для организации
         */
        Predicate<Organization> createFilter() {
            errorPrompt.textProperty().unbind();
            errorPrompt.setText("");
            T operand = converter.convert(operandField.getText());
            Operation selectedOperation = operationChoice.getSelectionModel().getSelectedItem();

            if (operand == null) {
                if (!operandField.getText().isEmpty()) {
                    errorPrompt.textProperty().bind(LocaleManager.getObservableStringByKey("invalidOperandLabel"));
                }
                return x -> true;
            }

            return x -> {
                T value = valueGetter.call(x);
                if (value == null) {
                    return false;
                }

                return selectedOperation.check(value.compareTo(operand));
            };
        }
    }

    /**
     * Вспомогательный класс для настройки фильтра по строковому значению.
     */
    private class StringFilterConfigurator extends FilterConfigurator {
        private TextField searchField;

        /**
         * Конструктор класса StringFilterConfigurator.
         * @param localeKey ключ для локализации
         * @param valueGetter функция для получения значения из организации
         */
        StringFilterConfigurator(String localeKey, Callback<Organization, String> valueGetter) {
            super(localeKey);
            searchField = new TextField();
            searchField.promptTextProperty().bind(LocaleManager.getObservableStringByKey("searchPromptLabel"));
            searchField.textProperty().addListener(o -> filterProperty().set(x -> valueGetter.call(x).contains(searchField.getCharacters())));
            setView(searchField);
        }
    }

    /**
     * Вспомогательный класс для настройки фильтра по дате.
     */
    private class DateFilterConfigurator extends FilterConfigurator {
        DateFilterConfigurator(String localeKey) {
            super(localeKey);
            DatePicker datePicker = new DatePicker();
            datePicker.valueProperty().addListener((o, oldV, newV) -> {
                if (newV == null) {
                    setFilter(x -> true);
                } else {
                    setFilter(x -> x.getCreationDate().equals(newV));
                }
            });
            setView(datePicker);
        }
    }

    /**
     * Перечисление для операций сравнения.
     */
    private enum Operation {
        GT(">", x -> x > 0), GE(">=", x -> x >= 0),
        EQ("=", x -> x == 0), LE("<=", x -> x <= 0),
        LT("<", x -> x < 0);

        private String verbal;
        private Predicate<Integer> operation;


        /**
         * Конструктор перечисления Operation.
         * @param verbal строковое представление операции
         * @param operation функция сравнения для операции
         */
        Operation(String verbal, Predicate<Integer> operation) {
            this.verbal = verbal;
            this.operation = operation;
        }

        /**
         * Проверяет, выполняется ли операция сравнения.
         * @param x результат сравнения
         * @return true, если операция выполняется, иначе false
         */
        boolean check(int x) {
            return operation.test(x);
        }

        /**
         * Возвращает строковое представление операции.
         * @return строковое представление операции
         */
        @Override
        public String toString() {
            return verbal;
        }
    }
}
