package Common.data;

import java.io.Serializable;

/**
 * Перечисление типов организаций
 */
public enum OrganizationType implements Serializable {
    COMMERCIAL,
    PUBLIC,
    GOVERNMENT,
    TRUST,
    OPEN_JOINT_STOCK_COMPANY;

    /**
     * Возвращает список имен констант перечисления OrganizationType в виде строки.
     * Каждое имя константы разделено запятой и пробелом.
     * @return Список имен констант перечисления OrganizationType в виде строки.
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (OrganizationType category : values()) {
            nameList.append(category.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}