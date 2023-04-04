package Common.util;

import Common.interfaces.Data;
import Common.data.Organization;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable, Data {

    private String messageToResponse;
    private Organization organizationToResponse;
    private List<Organization> collectionToResponse;

    public Response(String messageToResponse) {
        this.messageToResponse = messageToResponse;
    }

    public Response(String messageToResponse, Organization organizationToResponse) {
        this.messageToResponse = messageToResponse;
        this.organizationToResponse = organizationToResponse;
    }

    public Response(String messageToResponse, List<Organization> collectionToResponse) {
        this.messageToResponse = messageToResponse;
        this.collectionToResponse = collectionToResponse;
    }

    public Response(Organization organizationToResponse) {
        this.organizationToResponse = organizationToResponse;
    }

    public Response(List<Organization> collectionToResponse) {
        this.collectionToResponse = collectionToResponse;
    }

    public String getMessageToResponse() {
        return messageToResponse;
    }

    public Organization getOrganizationToResponse() {
        return organizationToResponse;
    }

    public List<Organization> getCollectionToResponse() {
        return collectionToResponse;
    }

    @Override
    public String getData() {
        return (messageToResponse == null ? "" : (getMessageToResponse()))
                + (organizationToResponse == null ? "" : ("\nДанные организации:\n" +  getOrganizationToResponse().toString()))
                + (collectionToResponse == null ? "" : ("\nКоллекция:\n" + getCollectionToResponse()));
    }

    @Override
    public String toString() {
        return "Ответ[" + messageToResponse + "]";
    }
}