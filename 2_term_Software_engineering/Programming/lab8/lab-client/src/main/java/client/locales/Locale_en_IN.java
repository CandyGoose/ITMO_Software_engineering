package client.locales;

import java.util.ListResourceBundle;

/**
 * Класс Locale_en_IN является ресурсным пакетом для предоставления локализованных строк на английском (индийском) языке.
 */
public class Locale_en_IN extends ListResourceBundle {
    private static final Object[][] contents = {
            {"organizationsTable", "Table view"},
            {"organizationsGraph", "Graph view"},
            {"languageMenuName", "Languages"},
            {"commandsMenuName", "Commands"},
            {"addCommand", "Add"},
            {"addCommandTitle", "Add an organization"},
            {"infoCommand", "Information"},
            {"infoResponse", "Collection type: {0}\nItem count: {1}"},
            {"uniqueEmployeesCountCommand", "Print unique employees count"},
            {"descendingAnnualTurnoverCommand", "Print field descending annual turnover "},
            {"clearCommand", "Clear"},
            {"removeFirstCommand", "Remove first organization"},
            {"annualTurnoverPrompt", "Enter the annualTurnover"},
            {"executeScriptCommand", "Execute script"},
            {"scriptChooserTitle", "Select a script to execute"},
            {"scriptResultTitle", "Script execution result"},
            {"commandResultLabel", "Result of command"},
            {"commandSuccessLabel", "Executed the command"},
            {"addressLabel", "Address"},
            {"addressPrompt", "Enter your address"},
            {"portLabel", "Port"},
            {"portPrompt", "Enter your port"},
            {"connectButton", "Connect"},
            {"invalidAddress", "The address you provided is invalid"},
            {"loginHeader", "Organization Manager"},
            {"loginSubHeader", "Please Login or Register"},
            {"loginLabel", "Login"},
            {"loginPrompt", "Your login"},
            {"passwordLabel", "Password"},
            {"passwordPrompt", "Your password"},
            {"usernameTaken", "This username is already taken"},
            {"incorrectCredentials", "Login or password is incorrect"},
            {"loginNotNull", "Fields cannot be empty"},
            {"loginButton", "Login"},
            {"logoutButton", "Logout"},
            {"registerButton", "Register"},
            {"disconnectButton", "Disconnect"},
            {"updateButton", "Update"},
            {"deleteButton", "Delete"},
            {"loggedInAsLabel", "Logged in as: {0}"},
            {"selectedLabel", "Selected: {0}"},
            {"filterLabel", "Filter"},
            {"noFilterLabel", "No filter applied"},
            {"noneLabel", "None"},
            {"idLabel", "ID"},
            {"nameLabel", "Name"},
            {"annualTurnoverLabel", "Annual turnover"},
            {"creationDateLabel", "Creation date"},
            {"xLabel", "X"},
            {"yLabel", "Y"},
            {"employeesCountLabel", "Employees count"},
            {"fullNameLabel", "Full name"},
            {"typeLabel", "Type"},
            {"streetLabel", "Street"},
            {"ownerLabel", "Owner"},
            {"operandLabel", "Operand"},
            {"invalidOperandLabel", "Invalid operand value"},
            {"searchPromptLabel", "Search for..."},
            {"invalidValue", "Invalid value provided"},
            {"fieldRequired", "This field is required"},
            {"idNotNull", "ID of organizations can not be null"},
            {"idGreaterThan0", "ID of organizations should be greater than 0"},
            {"organizationNameNotEmpty", "Name of organizations can not be empty"},
            {"organizationTypeNotEmpty", "Type of organizations can not be null"},
            {"annualTurnoverGreaterThan0", "Annual turnover of organizations should be greater than 0"},
            {"employeesCountGreaterThan0", "Employees count of organizations should be greater than 0"},
            {"coordinatesXNotNull", "Coordinate X can not be empty"},
            {"coordinatesYNotNull", "Coordinate Y can not be empty"},
            {"invalidResponse", "Invalid response"},
            {"error", "Error"},
            {"networkError", "An error occurred when trying to communicate with the server"},

    };

    /**
     * Метод getContents возвращает массив contents,
     * содержащий ключи и соответствующие им локализованные значения.
     *
     * @return Массив содержащий ключи и значения локализованных строк.
     */
    protected Object[][] getContents() {
        return contents;
    }
}