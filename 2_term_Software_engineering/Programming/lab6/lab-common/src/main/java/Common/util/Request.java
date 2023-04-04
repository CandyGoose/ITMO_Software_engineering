package Common.util;

import Common.interfaces.Data;
import Common.data.Organization;
import Common.data.OrganizationType;

import java.io.Serializable;

public class Request implements Serializable, Data {

    private final String commandName;
    private Long numericArgument;
    private Organization organizationArgument;
    private OrganizationType organizationType;

    public Request(String commandName) {
        this.commandName = commandName;
    }

    public Request(String commandName, Long numericArgument) {
        this.commandName = commandName;
        this.numericArgument = numericArgument;
    }

    public Request(String commandName, OrganizationType organizationType) {
        this.commandName = commandName;
        this.organizationType = organizationType;
    }

    public Request(String commandName, Organization organizationArgument) {
        this.commandName = commandName;
        this.organizationArgument = organizationArgument;
    }

    public Request(String commandName, Long numericArgument, Organization organizationArgument) {
        this.commandName = commandName;
        this.numericArgument = numericArgument;
        this.organizationArgument = organizationArgument;
    }

    public String getCommandName() {
        return commandName;
    }

    public Long getNumericArgument() {
        return numericArgument;
    }

    public Organization getOrganizationArgument() {
        return organizationArgument;
    }

    @Override
    public String getData(){
        return "Имя команды для отправки: " + commandName
                + (organizationArgument == null ? "" : ("\nИнформация об организации для отправки:\n " + organizationArgument) )
                + (numericArgument == null ? "" : ("\nЧисловой аргумент для отправки:\n " + numericArgument) )
                + (organizationType == null ? "" : ("\nТип организации для отправки:\n " + organizationType) ) ;
    }

    @Override
    public String toString() {
        return "Ответ[" + commandName + "]" ;
    }
}
