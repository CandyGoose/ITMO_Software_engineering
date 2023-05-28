package client.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;

/**
 * LocaleManager - класс, отвечающий за управление локализацией приложения.
 * Реализует выбор и установку текущей локали, а также предоставляет доступ к строковым ресурсам на выбранном языке.
 * Использует паттерн Singleton для обеспечения единственного экземпляра класса.
 */
public final class LocaleManager {
    /**
     * EN_IN - константа, представляющая локаль English (India).
     */
    public static final Locale EN_IN = Locale.forLanguageTag("en-IN");

    /**
     * RU - константа, представляющая локаль Russian.
     */
    public static final Locale RU = Locale.forLanguageTag("ru");

    /**
     * RO - константа, представляющая локаль Romanian.
     */
    public static final Locale RO = Locale.forLanguageTag("ro");

    /**
     * HU - константа, представляющая локаль Hungarian.
     */
    public static final Locale HU = new Locale("hu");


    /**
     * Объект, представляющий загруженные ресурсы локализации.
     */
    private static ResourceBundle bundle = ResourceBundle.getBundle("client.locales.Locale_en_IN");

    /**
     * Объект, представляющий текущую локаль приложения.
     */
    private static ObjectProperty<Locale> localeProperty = new SimpleObjectProperty<>(EN_IN);

    /**
     * Коллекция, содержащая значения ресурсов.
     * Ключ - ключ ресурса, значение - значение строки.
     */
    private static Map<String, StringProperty> observableStrings = new HashMap<>();

    private LocaleManager() { }

    /**
     * localeProperty - объект типа ObjectProperty, представляющий текущую локаль приложения.
     * Позволяет отслеживать и изменять текущую локаль.
     */
    public static ObjectProperty<Locale> localeProperty() {
        return localeProperty;
    }

    /**
     * getObservableStringByKey - метод для получения наблюдаемого значения строки по ключу.
     * Возвращает ObservableStringValue, представляющий строковый ресурс на выбранном языке.
     * Если строка с указанным ключом еще не была создана, создает новый StringProperty и добавляет его в observableStrings.
     * @param key ключ строки ресурса
     * @return ObservableStringValue - наблюдаемое значение строки
     */
    public static ObservableStringValue getObservableStringByKey(String key) {
        if (!observableStrings.containsKey(key)) {
            observableStrings.put(key, new SimpleStringProperty(bundle.getString(key)));
        }
        return observableStrings.get(key);
    }


    /**
     * setLocale - метод для установки новой локали.
     * Изменяет локаль по умолчанию, загружает соответствующий ResourceBundle для выбранной локали
     * и обновляет все наблюдаемые строки в соответствии с новой локалью.
     * @param newLocale новая локаль
     */
    public static void setLocale(Locale newLocale) {
        Locale.setDefault(newLocale);
        bundle = ResourceBundle.getBundle("client.locales.Locale", newLocale);
        observableStrings.keySet().forEach(x -> observableStrings.get(x).set(bundle.getString(x)));
        localeProperty.set(newLocale);
    }
}
