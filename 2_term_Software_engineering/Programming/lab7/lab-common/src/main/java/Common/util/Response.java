package Common.util;

import Common.data.Organization;
import Common.interfaces.Data;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

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
    private ConcurrentLinkedDeque<Organization> collectionToResponse;

    /**
     * Коллекция элементов, не принадлежащих клиенту
     */
    private ConcurrentLinkedDeque<Organization> alienElements;

    /**
     * Список дополнительной информации, содержащейся в ответе сервера
     */
    private List<String> info;

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
    public Response(String messageToResponse, ConcurrentLinkedDeque<Organization> collectionToResponse) {
        this.messageToResponse = messageToResponse;
        this.collectionToResponse = collectionToResponse;
    }

    /**
     * Конструктор класса для создания ответа сервера, содержащего только сообщение
     * @param messageToResponse сообщение, которое будет отправлено клиенту
     * @param info список дополнительной информации для клиента
     */
    public Response(String messageToResponse, List<String> info) {
        this.messageToResponse = messageToResponse;
        this.info = info;
    }

    /**
     * Конструктор класса для создания ответа сервера, содержащего сообщение и коллекцию элементов
     * @param messageToResponse сообщение, которое будет отправлено клиенту
     * @param usersElements коллекция элементов для отправки клиенту
     * @param alienElements коллекция элементов, не принадлежащих клиенту
     */
    public Response(String messageToResponse, ConcurrentLinkedDeque<Organization> usersElements, ConcurrentLinkedDeque<Organization> alienElements) {
        this.messageToResponse = messageToResponse;
        collectionToResponse = usersElements;
        this.alienElements = alienElements;
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
    public Response(ConcurrentLinkedDeque<Organization> collectionToResponse) {
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
    public ConcurrentLinkedDeque<Organization> getCollectionToResponse() {
        return collectionToResponse;
    }

    /**
     * Возвращает список дополнительной информации, содержащейся в ответе сервера
     * @return список дополнительной информации
     */
    public List<String> getInfo() {
        return info;
    }

    /**
     * Возвращает коллекцию элементов, не принадлежащих клиенту
     * @return коллекция элементов
     */
    public ConcurrentLinkedDeque<Organization> getAlienElements() {
        return alienElements;
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
                + (collectionToResponse == null ? "" : ("\nКоллекция:\n" + getCollectionToResponse()))
                + (alienElements == null ? "" :("\nКоллекции других пользователей:\n" +
                (getAlienElements().isEmpty() ? "В коллекциях других пользователей нет элементов" : getAlienElements())));
    }

    /**
     * Представляет ответ, полученный от сервера, в формате, удобном для чтения.
     */
    @Override
    public String toString() {
        return "Ответ[" + messageToResponse + "]";
    }
}