package enums;

public enum PhoneTypes {
    PHONE("телефону"),
    SMARTPHONE("смартфону");

    private final String whatPhone;

    public String getWhatPhone() {
        return whatPhone;
    }

    private PhoneTypes(String whatPhone) {
        this.whatPhone = whatPhone;
    }
}
