package Common.util;

import Common.data.Organization;
import Common.interfaces.Data;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Response implements Serializable, Data {

    private String messageToResponse;
    private Organization organizationToResponse;

    private ConcurrentLinkedDeque<Organization> collectionToResponse;
    private ConcurrentLinkedDeque<Organization> alienElements;
    private List<String> info;

    public Response(String messageToResponse) {
        this.messageToResponse = messageToResponse;
    }

    public Response(String messageToResponse, Organization organizationToResponse) {
        this.messageToResponse = messageToResponse;
        this.organizationToResponse = organizationToResponse;
    }

    public Response(String messageToResponse, ConcurrentLinkedDeque<Organization> collectionToResponse) {
        this.messageToResponse = messageToResponse;
        this.collectionToResponse = collectionToResponse;
    }

    public Response(String messageToResponse, List<String> info) {
        this.messageToResponse = messageToResponse;
        this.info = info;
    }

    public Response(String messageToResponse, ConcurrentLinkedDeque<Organization> usersElements, ConcurrentLinkedDeque<Organization> alienElements) {
        this.messageToResponse = messageToResponse;
        collectionToResponse = usersElements;
        this.alienElements = alienElements;
    }

    public Response(Organization organizationToResponse) {
        this.organizationToResponse = organizationToResponse;
    }

    public Response(ConcurrentLinkedDeque<Organization> collectionToResponse) {
        this.collectionToResponse = collectionToResponse;
    }

    public String getMessageToResponse() {
        return messageToResponse;
    }

    public Organization getOrganizationToResponse() {
        return organizationToResponse;
    }

    public ConcurrentLinkedDeque<Organization> getCollectionToResponse() {
        return collectionToResponse;
    }

    public List<String> getInfo() {
        return info;
    }

    public ConcurrentLinkedDeque<Organization> getAlienElements() {
        return alienElements;
    }


    @Override
    public String getData() {
        return (messageToResponse == null ? "" : (getMessageToResponse()))
                + (organizationToResponse == null ? "" : ("\nДанные организации:\n" +  getOrganizationToResponse().toString()))
                + (collectionToResponse == null ? "" : ("\nКоллекция:\n" + getCollectionToResponse()))
                + (alienElements == null ? "" :("\nКоллекции других пользователей:\n" +
                (getAlienElements().isEmpty() ? "В коллекциях других пользователей нет элементов" : getAlienElements())));
    }

    @Override
    public String toString() {
        return "Ответ[" + messageToResponse + "]";
    }
}