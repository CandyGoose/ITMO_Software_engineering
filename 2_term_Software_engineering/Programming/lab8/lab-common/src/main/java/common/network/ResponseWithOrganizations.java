package common.network;

import common.data.Organization;

/**
 * Класс, представляющий ответ на запрос с информацией об организациях.
 */
public class ResponseWithOrganizations extends Response {
    /**
     * Организация
     */
    private Organization[] organizations;

    /**
     * Создает новый объект ответа с информацией об организациях.
     *
     * @param organizations массив организаций.
     */
    public ResponseWithOrganizations(Organization[] organizations) {
        this.organizations = organizations;
    }

    /**
     * Возвращает организацию по индексу.
     *
     * @param i индекс организации.
     * @return организация.
     */
    public Organization getOrganization(int i) {
        return organizations[i];
    }

    /**
     * Возвращает количество организаций.
     *
     * @return количество организаций.
     */
    public int getOrganizationsCount() {
        return organizations.length;
    }

    /**
     * Возвращает массив организаций.
     *
     * @return массив организаций.
     */
    public Organization[] getOrganizations() {
        return organizations;
    }
}
