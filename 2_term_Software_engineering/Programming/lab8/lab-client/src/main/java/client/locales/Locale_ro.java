package client.locales;

import java.util.ListResourceBundle;

/**
 * Класс Locale_ro является ресурсным пакетом для предоставления локализованных строк на румынском языке.
 */
public class Locale_ro extends ListResourceBundle {
    private static final Object[][] contents = {
            {"organizationsTable", "Vizualizare tabel"},
            {"organizationsGraph", "Vizualizare grafică"},
            {"languageMenuName", "Limbi"},
            {"commandsMenuName", "Comenzi"},
            {"addCommand", "Adăugare"},
            {"addCommandTitle", "Adăugare organizație"},
            {"infoCommand", "Informații"},
            {"infoResponse", "Tip colecție: {0}\nNumăr de elemente: {1}"},
            {"uniqueEmployeesCountCommand", "Afișare număr angajați unic"},
            {"descendingAnnualTurnover", "Afișare câmp în ordine descrescătoare a cifrei de afaceri anuale"},
            {"clearCommand", "Ștergere"},
            {"removeFirstCommand", "Ștergere prima organizație"},
            {"executeScriptCommand", "Executare script"},
            {"scriptChooserTitle", "Selectați un script de executat"},
            {"scriptResultTitle", "Rezultatul execuției scriptului"},
            {"commandResultLabel", "Rezultatul comenzii"},
            {"commandSuccessLabel", "Comanda a fost executată cu succes"},
            {"addressLabel", "Adresă"},
            {"addressPrompt", "Introduceți adresa dumneavoastră"},
            {"portLabel", "Port"},
            {"portPrompt", "Introduceți portul dumneavoastră"},
            {"connectButton", "Conectare"},
            {"invalidAddress", "Adresa furnizată nu este validă"},
            {"loginHeader", "Gestionar Organizații"},
            {"loginSubHeader", "Vă rugăm să vă autentificați sau să vă înregistrați"},
            {"loginLabel", "Autentificare"},
            {"loginPrompt", "Numele de utilizator"},
            {"passwordLabel", "Parolă"},
            {"passwordPrompt", "Parola dumneavoastră"},
            {"usernameTaken", "Acest nume de utilizator este deja folosit"},
            {"incorrectCredentials", "Nume de utilizator sau parolă incorectă"},
            {"loginButton", "Autentificare"},
            {"logoutButton", "Deconectare"},
            {"registerButton", "Înregistrare"},
            {"disconnectButton", "Deconectare"},
            {"updateButton", "Actualizare"},
            {"deleteButton", "Ștergere"},
            {"loggedInAsLabel", "Autentificat ca: {0}"},
            {"selectedLabel", "Selectat: {0}"},
            {"filterLabel", "Filtru"},
            {"noFilterLabel", "Niciun filtru aplicat"},
            {"noneLabel", "Niciunul"},
            {"idLabel", "ID"},
            {"nameLabel", "Nume"},
            {"annualTurnoverLabel", "Cifra de afaceri anuală"},
            {"creationDateLabel", "Dată creare"},
            {"xLabel", "X"},
            {"yLabel", "Y"},
            {"employeesCountLabel", "Număr angajați"},
            {"fullNameLabel", "Nume complet"},
            {"typeLabel", "Tip"},
            {"streetLabel", "Stradă"},
            {"ownerLabel", "Proprietar"},
            {"operandLabel", "Operand"},
            {"invalidOperandLabel", "Valoare operand invalidă"},
            {"searchPromptLabel", "Căutare..."},
            {"invalidValue", "Valoare invalidă furnizată"},
            {"fieldRequired", "Acest câmp este obligatoriu"},
            {"idNotNull", "ID-ul organizațiilor nu poate fi nul"},
            {"idGreaterThan0", "ID-ul organizațiilor trebuie să fie mai mare decât 0"},
            {"organizationNameNotEmpty", "Numele organizațiilor nu poate fi gol"},
            {"organizationTypeNotEmpty", "Tipul organizațiilor nu poate fi nul"},
            {"annualTurnoverGreaterThan0", "Cifra de afaceri anuală a organizațiilor trebuie să fie mai mare decât 0"},
            {"employeesCountGreaterThan0", "Numărul de angajați al organizațiilor trebuie să fie mai mare decât 0"},
            {"coordinatesXNotNull", "Coordonata X nu poate fi goală"},
            {"coordinatesYNotNull", "Coordonata Y nu poate fi goală"},
            {"invalidResponse", "Răspuns invalid"},
            {"error", "Eroare"},
            {"networkError", "A apărut o eroare în timpul comunicării cu serverul"}
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