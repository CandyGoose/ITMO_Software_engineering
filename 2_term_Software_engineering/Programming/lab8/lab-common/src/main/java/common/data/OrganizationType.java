package common.data;

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
}

