package client.locales;

import java.util.ListResourceBundle;

/**
 * Класс Locale_ru является ресурсным пакетом для предоставления локализованных строк на русском языке.
 */
public class Locale_ru extends ListResourceBundle {
    private static final Object[][] contents = {
            {"organizationsTable", "Табличный вид"},
            {"organizationsGraph", "Графический вид"},
            {"languageMenuName", "Языки"},
            {"commandsMenuName", "Команды"},
            {"addCommand", "Добавить"},
            {"addCommandTitle", "Добавить организацию"},
            {"infoCommand", "Информация"},
            {"infoResponse", "Тип коллекции: {0}\nКоличество элементов: {1}"},
            {"uniqueEmployeesCountCommand", "Вывести уникальные значения количества сотрудников"},
            {"descendingAnnualTurnoverCommand", "Вывести поле с сортировкой по убыванию годового оборота"},
            {"clearCommand", "Очистить"},
            {"removeFirstCommand", "Удалить первую организацию"},
            {"executeScriptCommand", "Выполнить скрипт"},
            {"scriptChooserTitle", "Выберите скрипт для выполнения"},
            {"scriptResultTitle", "Результат выполнения скрипта"},
            {"commandResultLabel", "Результат команды"},
            {"commandSuccessLabel", "Команда выполнена"},
            {"addressLabel", "Адрес"},
            {"addressPrompt", "Введите адрес"},
            {"portLabel", "Порт"},
            {"portPrompt", "Введите ваш порт"},
            {"connectButton", "Подключиться"},
            {"invalidAddress", "Указанный вами адрес недействителен"},
            {"loginHeader", "Менеджер организаций"},
            {"loginSubHeader", "Пожалуйста, войдите или зарегистрируйтесь"},
            {"loginLabel", "Логин"},
            {"loginPrompt", "Ваш логин"},
            {"passwordLabel", "Пароль"},
            {"passwordPrompt", "Ваш пароль"},
            {"usernameTaken", "Это имя пользователя уже занято"},
            {"incorrectCredentials", "Неверный логин или пароль"},
            {"loginNotNull", "Поля не могут быть пустыми"},
            {"loginButton", "Войти"},
            {"logoutButton", "Выйти"},
            {"registerButton", "Зарегистрироваться"},
            {"disconnectButton", "Отключиться"},
            {"updateButton", "Обновить"},
            {"deleteButton", "Удалить"},
            {"loggedInAsLabel", "Вошли как: {0}"},
            {"selectedLabel", "Выбрано: {0}"},
            {"filterLabel", "Фильтр"},
            {"noFilterLabel", "Фильтр не применен"},
            {"noneLabel", "Нет"},
            {"idLabel", "ID"},
            {"nameLabel", "Название"},
            {"annualTurnoverLabel", "Годовой оборот"},
            {"creationDateLabel", "Дата создания"},
            {"xLabel", "X"},
            {"yLabel", "Y"},
            {"employeesCountLabel", "Количество сотрудников"},
            {"fullNameLabel", "Полное имя"},
            {"typeLabel", "Тип"},
            {"streetLabel", "Улица"},
            {"ownerLabel", "Владелец"},
            {"operandLabel", "Операнд"},
            {"invalidOperandLabel", "Недопустимое значение операнда"},
            {"searchPromptLabel", "Поиск..."},
            {"invalidValue", "Недопустимое значение"},
            {"fieldRequired", "Это поле обязательно для заполнения"},
            {"idNotNull", "ID организаций не может быть пустым"},
            {"idGreaterThan0", "ID организаций должен быть больше 0"},
            {"organizationNameNotEmpty", "Название организации не может быть пустым"},
            {"organizationTypeNotEmpty", "Тип организации не может быть пустым"},
            {"annualTurnoverGreaterThan0", "Годовой оборот организации должен быть больше 0"},
            {"employeesCountGreaterThan0", "Количество сотрудников организации должно быть больше 0"},
            {"coordinatesXNotNull", "Координата X не может быть пустой"},
            {"coordinatesYNotNull", "Координата Y не может быть пустой"},
            {"invalidResponse", "Недопустимый ответ"},
            {"error", "Ошибка"},
            {"networkError", "Произошла ошибка при попытке связаться с сервером"},
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