package common.data;

import java.io.Serializable;
import java.util.Objects;

import common.exceptions.InvalidFieldException;

/**
 * Класс, представляющий организацию.
 */
public class Organization implements Comparable<Organization>, Serializable {
    /**
     * Валидатор
     */
    public static final Validator VALIDATOR = new Validator();
    /**
     * id не может быть null, Значение поля должно быть больше 0
     * Значение этого поля должно быть уникальным
     * Значение этого поля должно генерироваться автоматически
     */
    private Long id;

    /**
     * Имя не может быть null
     * Строка не может быть пустой
     */
    private String name;

    /**
     * Дата не может быть null
     * Значение этого поля должно генерироваться автоматически
     */
    private java.time.LocalDateTime creationDate;

    /**
     * Координаты не могут быть null
     */
    private Coordinates coordinates;

    /**
     * Годовой оборот не может быть null
     * Значение поля должно быть больше 0
     */
    private Float annualTurnover;
    /**
     * Полное имя не может быть null
     * Строка не может быть пустой
     */
    private String fullName;

    /**
     * Количество сотрудников не может быть null
     * Значение поля должно быть больше 0
     */
    private Long employeesCount;

    /**
     * Адрес
     */
    private Address address;

    /**
     * Тип не может быть null
     */
    private OrganizationType type;

    /**
     * Владелец
     */
    private String owner = null;

    /**
     * Создает объект Organization.
     *
     * @param id идентификатор организации
     * @param name название организации
     * @param creationDate дата создания организации
     * @param coordinates координаты организации
     * @param annualTurnover годовой оборот организации
     * @param fullName полное название организации
     * @param employeesCount количество сотрудников в организации
     * @param type тип организации
     * @param address адрес организации
     * @throws InvalidFieldException если введенные значения некорректны
     */
    public Organization(Long id, String name, java.time.LocalDateTime creationDate, Coordinates coordinates, Float annualTurnover, String fullName, Long employeesCount, OrganizationType type, Address address) throws InvalidFieldException {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.address = address;
        VALIDATOR.validate(this);
    }

    /**
     * Создает пустой объект Organization с начальными значениями.
     */
    public Organization() {
        this.id = 1L;
        this.name = "";
        this.creationDate = null;
        this.coordinates = new Coordinates();
        this.annualTurnover = null;
        this.fullName = "";
        this.type = null;
        this.employeesCount = null;
        this.address = new Address();
    }

    /**
     * Возвращает идентификатор организации.
     *
     * @return идентификатор организации
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор организации.
     *
     * @param id идентификатор организации
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Возвращает название организации.
     *
     * @return название организации
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает годовой оборот организации.
     *
     * @return годовой оборот организации
     */
    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * Возвращает дату создания организации.
     *
     * @return дата создания организации
     */
    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает координаты организации.
     *
     * @return координаты организации
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает адрес организации.
     *
     * @return адрес организации
     */
    public Address getAddress() {return address;}

    /**
     * Возвращает полное название организации.
     *
     * @return полное название организации
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Возвращает количество сотрудников в организации.
     *
     * @return количество сотрудников в организации
     */
    public Long getEmployeesCount() {return employeesCount;}

    /**
     * Возвращает тип организации.
     *
     * @return тип организации
     */
    public OrganizationType getType() {return type;}

    /**
     * Устанавливает владельца организации.
     *
     * @param owner владелец организации
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Возвращает владельца организации.
     *
     * @return владелец организации
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Возвращает строковое представление организации.
     *
     * @return строковое представление организации
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Organization #");
        sb.append(id);
        sb.append(" {\n\tname=");
        sb.append(name);
        sb.append(",\n\tcreationDate=");
        sb.append(creationDate);
        sb.append(",\n\tcoordinates=");
        sb.append(coordinates.toString().replaceAll("\\n", "\n\t"));
        sb.append(",\n\tannualTurnover=");
        sb.append(annualTurnover);
        sb.append(" {\n\tfullName=");
        sb.append(fullName);
        sb.append(",\n\temployeesCount=");
        sb.append(employeesCount);
        sb.append(",\n\taddress=");
        sb.append(address.toString().replaceAll("\\n", "\n\t"));
        sb.append(",\n\ttype=");
        sb.append(type.toString().replaceAll("\\n", "\n\t"));
        sb.append(",\n\towner=");
        sb.append(owner);
        sb.append("\n}");

        return sb.toString();
    }

    /**
     * Проверяет, является ли указанный объект равным текущему объекту.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, в противном случае - false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Organization)) {
            return false;
        }
        Organization t = (Organization) obj;

        return id.equals(t.id)
                && name.equals(t.name)
                && (Objects.equals(annualTurnover, t.annualTurnover))
                && fullName.equals(t.fullName)
                && (Objects.equals(employeesCount, t.employeesCount))
                && creationDate.equals(t.creationDate)
                && coordinates.equals(t.coordinates)
                && type.equals(t.type);
    }

    /**
     * Возвращает хеш-код объекта.
     *
     * @return хеш-код объекта
     */
    @Override
    public int hashCode() {
        final int k = 31;
        final int a = 7;
        int hash = a;
        hash = k * hash + id.hashCode();
        if (annualTurnover != null) {
            hash = k * hash + annualTurnover.hashCode();
        }
        if (employeesCount != null) {
            hash = k * hash + employeesCount.hashCode();
        }
        hash = k * hash + name.hashCode();
        hash = k * hash + creationDate.hashCode();
        hash = k * hash + coordinates.hashCode();
        hash = k * hash + fullName.hashCode();
        hash = k * hash + type.hashCode();
        return hash;
    }

    /**
     * Сравнивает текущую организацию с указанной организацией.
     *
     * @param o организация для сравнения
     * @return отрицательное целое число, ноль или положительное целое число, если текущая организация меньше, равна
     * или больше указанной организации соответственно
     */
    @Override
    public int compareTo(Organization o) {
        if (this.annualTurnover == null || o.annualTurnover == null || (this.annualTurnover.equals(o.annualTurnover))) {
            return Long.compare(this.id, o.id);
        }
        return Float.compare(annualTurnover, o.annualTurnover);
    }

    /**
     * Внутренний класс для проверки валидности организации.
     */
    public static class Validator implements AbstractValidator<Organization> {

        /**
         * Проверяет валидность организации.
         *
         * @param organization организация для проверки
         * @throws InvalidFieldException если поле организации недопустимо
         */
        public void validate(Organization organization) throws InvalidFieldException {
            validateId(organization.id);
            validateName(organization.name);
            validateCreationDate(organization.creationDate);
            validateCoordinates(organization.coordinates);
            validateAnnualTurnover(organization.annualTurnover);
            validateFullName(organization.fullName);
            validateEmployeesCount(organization.employeesCount);
            validateType(organization.type);
            validateAddress(organization.address);
        }

        /**
         * Проверяет валидность идентификатора организации.
         *
         * @param id идентификатор организации
         * @throws InvalidFieldException если идентификатор недопустим
         */
        public void validateId(Long id) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(id, "идентификатор организации не может быть пустым", "idNotNull");
            if (id <= 0) {
                throw new InvalidFieldException("идентификатор организации должен быть больше 0", "idGreaterThan0");
            }
        }

        /**
         * Проверяет валидность названия организации.
         *
         * @param name название организации
         * @throws InvalidFieldException если название недопустимо
         */
        public void validateName(String name) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(name, "имя организации не может быть пустым", "organizationNameNotEmpty");
            if (name.length() == 0) {
                throw new InvalidFieldException("название организации не может быть пустой строкой", "organizationNameNotEmpty");
            }
        }

        /**
         * Проверяет валидность координат организации.
         *
         * @param coordinates координаты организации
         * @throws InvalidFieldException если координаты недопустимы
         */
        public void validateCoordinates(Coordinates coordinates) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(coordinates, "координаты организаций не могут быть пустыми", null);
        }

        /**
         * Проверяет валидность типа организации.
         *
         * @param type тип организации
         * @throws InvalidFieldException если тип недопустим
         */
        public void validateType(OrganizationType type) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(type, "тип организаций не может быть пустым", "organizationTypeNotEmpty");
        }


        /**
         * Проверяет валидность адреса организации.
         *
         * @param address адрес организации
         * @throws InvalidFieldException если адрес недопустим
         */
        public void validateAddress(Address address) throws InvalidFieldException {
        }

        /**
         * Проверяет валидность годового оборота организации.
         *
         * @param annualTurnover годовой оборот организации
         * @throws InvalidFieldException если годовой оборот недопустим
         */
        public void validateAnnualTurnover(Float annualTurnover) throws InvalidFieldException {
            if (annualTurnover == null || annualTurnover <= 0) {
                throw new InvalidFieldException("годовой оборот организаций должен быть больше 0", "annualTurnoverGreaterThan0");
            }
        }

        /**
         * Проверяет валидность полного названия организации.
         *
         * @param fullName полное название организации
         * @throws InvalidFieldException если полное название недопустимо
         */
        public void validateFullName(String fullName) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(fullName, "полное название организации не может быть пустым", "organizationNameNotEmpty");
            if (fullName.length() == 0) {
                throw new InvalidFieldException("полное название организации не может быть пустой строкой", "organizationNameNotEmpty");
            }
        }

        /**
         * Проверяет валидность количества сотрудников в организации.
         *
         * @param employeesCount количество сотрудников в организации
         * @throws InvalidFieldException если количество сотрудников недопустимо
         */
        public void validateEmployeesCount(Long employeesCount) throws InvalidFieldException {
            if (employeesCount == null || employeesCount <= 0) {
                throw new InvalidFieldException("количество сотрудников в организациях должно быть больше 0", "annualTurnoverGreaterThan0");
            }
        }

        /**
         * Проверяет валидность даты создания организации.
         *
         * @param creationDate дата создания организации
         * @throws InvalidFieldException если дата недопустима
         */
        public void validateCreationDate(java.time.LocalDateTime creationDate) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(creationDate, "дата создания организаций не может быть нулевой", null);
        }
    }
}
