package common.data;

import java.time.LocalDateTime;

import common.exceptions.InvalidFieldException;
import common.network.BasicUserIO;

/**
 * Класс, содержащий статические методы для создания экземпляра класса Organization.
 */
public final class OrganizationMaker {
    /**
     * Объект OrganizationMaker
     */
    private OrganizationMaker() {
    }

    /**
     * Создает экземпляр класса Organization на основе ввода пользователя.
     *
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @param id идентификатор организации
     * @return созданный экземпляр класса Organization
     * @throws InvalidFieldException если значения полей недопустимы
     */
    public static Organization parseOrganization(BasicUserIO io, Long id) throws InvalidFieldException {
        Organization.VALIDATOR.validateId(id);

        return new Organization(
            id,
            BasicParsers.Repeater.doGet(OrganizationMaker::parseName, io),
            LocalDateTime.now(),
            BasicParsers.Repeater.doGet(
                io_ -> parseCoordinates(
                    io_,
                    "Coordinates",
                        Organization.VALIDATOR::validateCoordinates
                ),
                io
            ),
            BasicParsers.Repeater.doGet(OrganizationMaker::parseAnnualTurnover, io),
            BasicParsers.Repeater.doGet(OrganizationMaker::parseFullName, io),
            BasicParsers.Repeater.doGet(OrganizationMaker::parseEmployeesCount, io),
            OrganizationType.valueOf(BasicParsers.Repeater.doGet(OrganizationMaker::parseType, io)),

            BasicParsers.Repeater.doGet(
            io_ -> parseAddress(
                    io_,
                    "Address"
            ),
            io)
        );
    }

    /**
     * Запрашивает у пользователя название организации и возвращает его.
     *
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return название организации
     * @throws InvalidFieldException если название недопустимо
     */
    public static String parseName(BasicUserIO io) throws InvalidFieldException {
        String res = BasicParsers.parseString(io, "Name: ");
        Organization.VALIDATOR.validateName(res);
        return res;
    }

    /**
     * Запрашивает у пользователя годовой оборот организации и возвращает его.
     *
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return годовой оборот организации
     * @throws InvalidFieldException если годовой оборот недопустим
     */
    public static Float parseAnnualTurnover(BasicUserIO io) throws InvalidFieldException {
        Float res = BasicParsers.parseFloat(io, "Annual turnover: ", "Годовой оборот должен быть больше нуля.");
        Organization.VALIDATOR.validateAnnualTurnover(res);
        return res;
    }

    /**
     * Запрашивает у пользователя полное название организации и возвращает его.
     *
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return полное название организации
     * @throws InvalidFieldException если полное название недопустимо
     */
    public static String parseFullName(BasicUserIO io) throws InvalidFieldException {
        String res = BasicParsers.parseString(io, "Full name: ");
        Organization.VALIDATOR.validateFullName(res);
        return res;
    }

    /**
     * Запрашивает у пользователя количество сотрудников в организации и возвращает его.
     *
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return количество сотрудников в организации
     * @throws InvalidFieldException если количество сотрудников недопустимо
     */
    public static Long parseEmployeesCount(BasicUserIO io) throws InvalidFieldException {
        Long res = BasicParsers.parseLong(io, "Employees count: ", "Количество сотрудников должно быть больше 0.");
        Organization.VALIDATOR.validateEmployeesCount(res);
        return res;
    }

    /**
     * Запрашивает у пользователя координаты и возвращает их.
     *
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @param prompt сообщение для отображения перед вводом координат
     * @param validator объект AbstractValidator для валидации координат
     * @return координаты
     * @throws InvalidFieldException если координаты недопустимы
     */
    public static Coordinates parseCoordinates(BasicUserIO io, String prompt, AbstractValidator<Coordinates> validator) throws InvalidFieldException {
        try {
            io.writeln(prompt);
            Coordinates coordinates = CoordinatesMaker.parseCoordinates(io);
            validator.validate(coordinates);
            return coordinates;
        } catch (InvalidFieldException e) {
            throw new InvalidFieldException("Не удалось создать координаты.", e);
        }
    }

    /**
     * Запрашивает у пользователя тип организации и возвращает его.
     *
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return тип организации
     * @throws InvalidFieldException если тип недопустим
     */
    public static String parseType(BasicUserIO io) throws InvalidFieldException {
        String res = BasicParsers.parseString(io, "Type: ");
        Organization.VALIDATOR.validateType(OrganizationType.valueOf(res));
        return res;
    }

    /**
     * Запрашивает у пользователя адрес и возвращает его.
     *
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @param prompt сообщение для отображения перед вводом адреса
     * @return адрес
     * @throws InvalidFieldException если адрес недопустим
     */
    public static Address parseAddress(BasicUserIO io, String prompt) throws InvalidFieldException {
        try {
            io.writeln(prompt);
            Address address = AddressMaker.parseAddress(io);
            return address;
        } catch (InvalidFieldException e) {
            throw new InvalidFieldException("Не удалось создать адрес", e);
        }
    }
}
