package client.util;

import client.LocaleManager;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

public class LocalizationUtil {
    public static void bindTextToLocale(Property<String> property, String key) {
        if (property instanceof StringProperty) {
            property.bind(LocaleManager.getObservableStringByKey(key));
        } else {
            throw new IllegalArgumentException("Property type not supported for text binding: " + property.getClass());
        }
    }
}
