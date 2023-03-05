package data;

/**
 * Перечисление типов организаций
 */
public enum OrganizationType {
    COMMERCIAL,
    PUBLIC,
    GOVERNMENT,
    TRUST,
    OPEN_JOINT_STOCK_COMPANY;


    /**
     * Эта функция возвращает разделенный запятыми список имен всех значений
     *
     * @return types
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (OrganizationType category : values()) {
            nameList.append(category.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}