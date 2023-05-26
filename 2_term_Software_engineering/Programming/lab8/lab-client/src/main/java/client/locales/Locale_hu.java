package client.locales;

import java.util.ListResourceBundle;

/**
 * Класс Locale_hu является ресурсным пакетом для предоставления локализованных строк на венгерском языке.
 */
public class Locale_hu extends ListResourceBundle {
    private static final Object[][] contents = {
            {"organizationsTable", "Táblázat nézet"},
            {"organizationsGraph", "Grafikon nézet"},
            {"languageMenuName", "Nyelvek"},
            {"commandsMenuName", "Parancsok"},
            {"addCommand", "Hozzáadás"},
            {"addCommandTitle", "Szervezet hozzáadása"},
            {"infoCommand", "Információ"},
            {"infoResponse", "Gyűjtemény típusa: {0}\nElemek száma: {1}"},
            {"uniqueEmployeesCountCommand", "Egyedi alkalmazottak számának listázása"},
            {"descendingAnnualTurnoverCommand", "Csökkenő sorrendben listázza az éves forgalmat"},
            {"clearCommand", "Törlés"},
            {"removeFirstCommand", "Az első szervezet törlése"},
            {"executeScriptCommand", "Parancsfájl végrehajtása"},
            {"scriptChooserTitle", "Válasszon ki egy végrehajtandó parancsfájlt"},
            {"scriptResultTitle", "Parancsfájl végrehajtás eredménye"},
            {"commandResultLabel", "Parancs eredménye"},
            {"commandSuccessLabel", "A parancs sikeresen végrehajtva"},
            {"addressLabel", "Cím"},
            {"addressPrompt", "Adja meg az ön címét"},
            {"portLabel", "Port"},
            {"portPrompt", "Adja meg a portot"},
            {"connectButton", "Kapcsolódás"},
            {"invalidAddress", "A megadott cím érvénytelen"},
            {"loginHeader", "Szervezetkezelő"},
            {"loginSubHeader", "Kérjük, jelentkezzen be vagy regisztráljon"},
            {"loginLabel", "Bejelentkezés"},
            {"loginPrompt", "A bejelentkezési neve"},
            {"passwordLabel", "Jelszó"},
            {"passwordPrompt", "A jelszava"},
            {"usernameTaken", "Ez a felhasználónév már foglalt"},
            {"incorrectCredentials", "A bejelentkezési adatok helytelenek"},
            {"loginNotNull", "A mezők nem lehetnek üresek"},
            {"loginButton", "Bejelentkezés"},
            {"logoutButton", "Kijelentkezés"},
            {"registerButton", "Regisztráció"},
            {"disconnectButton", "Kapcsolat bontása"},
            {"updateButton", "Frissítés"},
            {"deleteButton", "Törlés"},
            {"loggedInAsLabel", "Bejelentkezve mint: {0}"},
            {"selectedLabel", "Kiválasztva: {0}"},
            {"filterLabel", "Szűrő"},
            {"noFilterLabel", "Nincs alkalmazott szűrő"},
            {"noneLabel", "Nincs"},
            {"idLabel", "Azonosító"},
            {"nameLabel", "Név"},
            {"annualTurnoverLabel", "Éves forgalom"},
            {"creationDateLabel", "Létrehozás dátuma"},
            {"xLabel", "X"},
            {"yLabel", "Y"},
            {"employeesCountLabel", "Alkalmazottak száma"},
            {"fullNameLabel", "Teljes név"},
            {"typeLabel", "Típus"},
            {"streetLabel", "Utca"},
            {"ownerLabel", "Tulajdonos"},
            {"operandLabel", "Operandus"},
            {"invalidOperandLabel", "Érvénytelen operandus érték"},
            {"searchPromptLabel", "Keresés..."},
            {"invalidValue", "Érvénytelen érték megadva"},
            {"fieldRequired", "Ez a mező kötelező"},
            {"idNotNull", "A szervezetek azonosítója nem lehet null"},
            {"idGreaterThan0", "A szervezetek azonosítója nagyobb kell legyen, mint 0"},
            {"organizationNameNotEmpty", "A szervezetek neve nem lehet üres"},
            {"organizationTypeNotEmpty", "A szervezetek típusa nem lehet null"},
            {"annualTurnoverGreaterThan0", "A szervezetek éves forgalma nagyobb kell legyen, mint 0"},
            {"employeesCountGreaterThan0", "A szervezetek alkalmazottainak száma nagyobb kell legyen, mint 0"},
            {"coordinatesXNotNull", "Az X koordináta nem lehet üres"},
            {"coordinatesYNotNull", "Az Y koordináta nem lehet üres"},
            {"invalidResponse", "Érvénytelen válasz"},
            {"error", "Hiba"},
            {"networkError", "Hiba történt a szerverrel való kommunikáció során"}
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