package common.network;

import common.data.Organization;

/**
 * Класс, представляющий тело запроса с организацией.
 */
public class RequestBodyWithOrganization extends RequestBody {
    /**
     * Организация
     */
    private Organization organization;

    /**
     * Создает новый объект тела запроса с организацией.
     *
     * @param args аргументы запроса.
     * @param organization организация.
     */
    public RequestBodyWithOrganization(String[] args, Organization organization) {
        super(args);
        this.organization = organization;
    }

    /**
     * Возвращает организацию.
     *
     * @return организация.
     */
    public Organization getOrganization() {
        return organization;
    }
}
