package objects;

import enums.PhoneTypes;

public class Phone {
    private final PhoneTypes phoneType;
    private final String phoneModel;

    public PhoneTypes getPhoneType() {
        return phoneType;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public Phone(PhoneTypes phoneType, String phoneModel) {
        this.phoneType = phoneType;
        this.phoneModel = phoneModel;
    }

    @Override
    public boolean equals(Object o) {
        return getClass().equals(o.getClass()) &&
                getPhoneModel().equals(((Phone) o).getPhoneModel()) &&
                getPhoneType().equals(((Phone) o).getPhoneType());
    }
}
