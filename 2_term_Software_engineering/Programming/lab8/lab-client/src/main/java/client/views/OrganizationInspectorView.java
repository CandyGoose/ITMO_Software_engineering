package client.views;

import java.time.LocalDateTime;

import client.GraphicClient;
import client.LocaleManager;
import client.util.NumberStringConverter;
import client.util.StringConverter;
import common.exceptions.InvalidFieldException;
import common.data.*;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Priority;


/**
 * Класс OrganizationInspectorView представляет представление для просмотра и редактирования информации об организации.
 * Он отображает поля и значения организации, а также предоставляет методы для получения отредактированной организации.
 */
public class OrganizationInspectorView {
    private static final int GAP = 3;
    private static final int HORIZONTAL_PADDING = 10;
    private GraphicClient client;
    private ObjectProperty<Organization> organizationToInspectProperty;
    private ReadOnlyBooleanWrapper organizationReadyProperty = new ReadOnlyBooleanWrapper(false);
    private BooleanProperty organizationIsEditableProperty = new SimpleBooleanProperty(false);
    private BooleanProperty typeFieldEditableProperty = new SimpleBooleanProperty(false);

    private Parent view;
    private ValidationField<String> nameField;
    private ValidationField<Float> annualTurnoverField;
    private ValidationField<String> fullNameField;
    private ValidationField<Long> employeesCountField;
    private ValidationField<Float> xField;
    private ValidationField<Float> yField;
    private ChoiceBox<OrganizationType> typeField;
    private ValidationField<String> streetField;

    /**
     * Создает новый объект `OrganizationInspectorView`.
     *
     * @param client клиентская часть приложения
     * @param organizationToInspectProperty свойство, содержащее организацию для просмотра и редактирования
     */
    public OrganizationInspectorView(GraphicClient client, ObjectProperty<Organization> organizationToInspectProperty) {
        this.client = client;
        this.organizationToInspectProperty = organizationToInspectProperty;
        organizationToInspectProperty.addListener((o, oldV, newV) -> fillFields(newV));
        organizationIsEditableProperty.bind(Bindings.createBooleanBinding(OrganizationInspectorView.this::organizationIsEditable, client.authProperty(), organizationToInspectProperty));
        createAllFields();
        Label typeLabel = new Label();
        typeField = new ChoiceBox<>(FXCollections.observableArrayList(OrganizationType.values()));
        typeField.disableProperty().bind(typeFieldEditableProperty.not());
        typeLabel.textProperty().bind(LocaleManager.getObservableStringByKey("typeLabel"));
        Pane choiceBoxContainer = new Pane(typeField);
        typeField.prefWidthProperty().bind(choiceBoxContainer.widthProperty());

        organizationReadyProperty.bind(Bindings.and(nameField.valueReadyProperty,
                                        Bindings.and(annualTurnoverField.valueReadyProperty,
                                        Bindings.and(fullNameField.valueReadyProperty,
                                        Bindings.and(employeesCountField.valueReadyProperty,
                                        Bindings.and(xField.valueReadyProperty,
                                        Bindings.and(yField.valueReadyProperty,
                                        Bindings.and(streetField.valueReadyProperty,
                                        Bindings.and(typeField.getSelectionModel().selectedItemProperty().isNotNull(),
                                        organizationIsEditableProperty)))))))));

        VBox box = new VBox(GAP);
        box.setPadding(new Insets(GAP, HORIZONTAL_PADDING, GAP, HORIZONTAL_PADDING));
        box.getChildren().addAll(
                nameField.getComponent(),
                annualTurnoverField.getComponent(),
                fullNameField.getComponent(),
                employeesCountField.getComponent(),
                xField.getComponent(),
                yField.getComponent(),
                streetField.getComponent(),
                typeLabel, choiceBoxContainer
        );
        this.view = box;
    }

    /**
     * Возвращает представление организации.
     *
     * @return представление организации
     */
    public Parent getView() {
        return view;
    }

    /**
     * Возвращает свойство готовности организации.
     * Готовность организации означает, что все поля заполнены корректно и организация может быть сохранена.
     *
     * @return свойство готовности организации
     */
    public ReadOnlyBooleanProperty organizationReadyProperty() {
        return organizationReadyProperty.getReadOnlyProperty();
    }

    /**
     * Возвращает объект организации с учетом внесенных изменений.
     * Если введены некорректные значения, возвращается исходный объект организации без изменений.
     *
     * @return объект организации
     */
    public Organization getOrganization() {
        try {
            Organization newOrganization = new Organization(
                organizationToInspectProperty.get().getId(),
                nameField.getValue(),
                LocalDateTime.now(),
                new Coordinates(
                    xField.getValue(),
                    yField.getValue()
                ),
                annualTurnoverField.getValue(),
                fullNameField.getValue(),
                employeesCountField.getValue(),
                typeField.getValue(),
                new Address(
                    streetField.getValue()
                )
            );
            newOrganization.setOwner(organizationToInspectProperty.get().getOwner());
            return newOrganization;
        } catch (InvalidFieldException e) {
            return organizationToInspectProperty.get();
        }
    }

    /**
     * Создает все поля.
     */
    private void createAllFields() {
        nameField = new ValidationField<>("nameLabel", x -> (x.isEmpty() ? null : x), Organization.VALIDATOR::validateName);
        annualTurnoverField = new ValidationField<>("annualTurnoverLabel", new NumberStringConverter<>(Float::parseFloat), Organization.VALIDATOR::validateAnnualTurnover);
        fullNameField = new ValidationField<>("fullNameLabel", x -> (x.isEmpty() ? null : x), Organization.VALIDATOR::validateFullName);
        employeesCountField = new ValidationField<>("employeesCountLabel", new NumberStringConverter<>(Long::parseLong), Organization.VALIDATOR::validateEmployeesCount);
        xField = new ValidationField<>("xLabel", new NumberStringConverter<>(Float::parseFloat), Coordinates.VALIDATOR::validateX);
        yField = new ValidationField<>("yLabel", new NumberStringConverter<>(Float::parseFloat), Coordinates.VALIDATOR::validateY);
        streetField = new ValidationField<>("streetLabel", x -> (x.isEmpty() ? null : x), Address.VALIDATOR::validateStreet);
        typeField = new ChoiceBox<>(FXCollections.observableArrayList(OrganizationType.values()));
        typeFieldEditableProperty.bind(organizationIsEditableProperty);
    }

    /**
     * Заполняет поля данными организации.
     *
     * @param organization организация
     */
    private void fillFields(Organization organization) {
        if (organization == null) {
            nameField.emptyValue();
            annualTurnoverField.emptyValue();
            fullNameField.emptyValue();
            employeesCountField.emptyValue();
            xField.emptyValue();
            yField.emptyValue();
            streetField.emptyValue();
            typeField.getSelectionModel().clearSelection();
            return;
        }

        nameField.setValue(organization.getName());
        annualTurnoverField.setValue(organization.getAnnualTurnover());
        fullNameField.setValue(organization.getName());
        employeesCountField.setValue(organization.getEmployeesCount());
        xField.setValue(organization.getCoordinates().getX());
        yField.setValue(organization.getCoordinates().getY());
        streetField.setValue(organization.getAddress().getStreet());
        typeField.setValue(organization.getType());
        typeField.disableProperty().bind(typeFieldEditableProperty.not());
    }


    /**
     * Проверяет, может ли организация редактироваться.
     *
     * @return true, если организация может быть отредактирована, иначе false
     */
    private boolean organizationIsEditable() {
        if (organizationToInspectProperty.get() == null || client.getAuth() == null) {
            return false;
        }

        return organizationToInspectProperty.get().getOwner().equals(client.getAuth().getLogin());
    }

    /**
     * Класс, представляющий поле валидации.
     *
     * @param <T> тип значения поля
     */
    private class ValidationField<T> {
        private StringConverter<T> converter;
        private AbstractValidator<T> validator;
        private Node component;
        private TextField valueField;
        private Label promptLabel = new Label();
        private BooleanProperty valueReadyProperty = new SimpleBooleanProperty(false);

        /**
         * Создает поле валидации.
         *
         * @param localeKey ключ локализации
         * @param converter конвертер значения
         * @param validator валидатор значения
         */
        ValidationField(String localeKey, StringConverter<T> converter, AbstractValidator<T> validator) {
            this.converter = converter;
            this.validator = validator;
            Label fieldLabel = new Label();
            fieldLabel.textProperty().bind(LocaleManager.getObservableStringByKey(localeKey));
            promptLabel.setTextFill(Color.RED);
            valueField = new TextField();
            valueField.editableProperty().bind(organizationIsEditableProperty);
            VBox mainBox = new VBox(GAP);
            mainBox.getChildren().addAll(fieldLabel, valueField, promptLabel);
            component = mainBox;
            valueField.textProperty().addListener((o, oldV, newV) -> validateAndUpdate(newV));
        }

        /**
         * Проверяет и обновляет значение поля.
         *
         * @param newV новое значение
         */
        void validateAndUpdate(String newV) {
            promptLabel.textProperty().unbind();
            promptLabel.setText("");
            valueReadyProperty.set(false);

            if (organizationToInspectProperty.get() == null) {
                return;
            }

            T value = converter.convert(newV);
            if (value == null && !newV.isEmpty()) {
                promptLabel.textProperty().bind(LocaleManager.getObservableStringByKey("invalidValue"));
                return;
            }

            try {
                validator.validate(value);
            } catch (InvalidFieldException e) {
                promptLabel.textProperty().bind(LocaleManager.getObservableStringByKey(e.getLocaleKey()));
                return;
            }

            valueReadyProperty.set(true);
        }

        /**
         * Очищает значение поля.
         */
        void emptyValue() {
            valueField.setText("");
        }

        /**
         * Устанавливает значение поля.
         *
         * @param value значение
         */
        void setValue(T value) {
            valueField.setText(value == null ? "" : value.toString());
            validateAndUpdate(valueField.getText());
        }

        /**
         * Возвращает значение поля.
         *
         * @return значение поля
         */
        T getValue() {
            return converter.convert(valueField.getText());
        }

        /**
         * Возвращает компонент поля.
         *
         * @return компонент поля
         */
        Node getComponent() {
            return component;
        }
    }
}
