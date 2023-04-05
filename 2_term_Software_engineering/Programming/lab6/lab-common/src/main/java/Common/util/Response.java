package Common.util;

import Common.interfaces.Data;
import Common.data.Organization;

import java.io.Serializable;
import java.util.List;

/**
 * Класс Response - класс, содержащий информацию для ответа на запрос.
 */
public class Response implements Serializable, Data {

    /**
     * Сообщение, отправляемое в ответ на запрос.
     */
    private String messageToResponse;

    /**
     * Данные об организации, отправляемые в ответ на запрос.
     */
    private Organization organizationToResponse;

    /**
     * Данные коллекции, отправляемые в ответ на запрос.
     */
    private List<Organization> collectionToResponse;

    /**
     * Конструктор класса Response, принимающий сообщение для ответа.
     *
     * @param messageToResponse сообщение для ответа
     */
    public Response(String messageToResponse) {
        this.messageToResponse = messageToResponse;
    }

    /**
     * Конструктор класса Response, принимающий сообщение и информацию об организации для ответа.
     *
     * @param messageToResponse сообщение для ответа
     * @param organizationToResponse информация об организации для ответа
     */
    public Response(String messageToResponse, Organization organizationToResponse) {
        this.messageToResponse = messageToResponse;
        this.organizationToResponse = organizationToResponse;
    }

    /**
     * Конструктор класса Response, принимающий сообщение и коллекцию организаций для ответа.
     *
     * @param messageToResponse сообщение для ответа.
     * @param collectionToResponse коллекция организаций для ответа
     */
    public Response(String messageToResponse, List<Organization> collectionToResponse) {
        this.messageToResponse = messageToResponse;
        this.collectionToResponse = collectionToResponse;
    }

    /**
     * Конструктор класса Response, принимающий информацию об организации для ответа.
     *
     * @param organizationToResponse информация об организации для ответа
     */
    public Response(Organization organizationToResponse) {
        this.organizationToResponse = organizationToResponse;
    }

    /**
     * Конструктор класса Response, принимающий коллекцию организаций для ответа.
     *
     * @param collectionToResponse коллекция организаций для ответа
     */
    public Response(List<Organization> collectionToResponse) {
        this.collectionToResponse = collectionToResponse;
    }

    /**
     * Метод, возвращающий сообщение для ответа.
     *
     * @return сообщение для ответа
     */
    public String getMessageToResponse() {
        return messageToResponse;
    }

    /**
     * Метод, возвращающий информацию об организации для ответа.
     *
     * @return информация об организации для ответа
     */
    public Organization getOrganizationToResponse() {
        return organizationToResponse;
    }

    /**
     * Метод, возвращающий коллекцию организаций для ответа.
     *
     * @return коллекция организаций для ответа
     */
    public List<Organization> getCollectionToResponse() {
        return collectionToResponse;
    }


    /**
     * Метод, возвращающий информацию для отправки.
     *
     * @return информация для отправки
     */
    @Override
    public String getData() {
        return (messageToResponse == null ? "" : (getMessageToResponse()))
                + (organizationToResponse == null ? "" : ("\nДанные организации:\n" +  getOrganizationToResponse().toString()))
                + (collectionToResponse == null ? "" : ("\nКоллекция:\n" + getCollectionToResponse()));
    }

    /**
     * Представляет ответ, полученный от сервера, в формате, удобном для чтения.
     */
    @Override
    public String toString() {
        return "Ответ[" + messageToResponse + "]";
    }
}