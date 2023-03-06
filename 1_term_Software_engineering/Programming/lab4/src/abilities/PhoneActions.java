package abilities;

import objects.Phone;
import people.Observers;

public interface PhoneActions {
    void walkToPhone(Phone phone, boolean isFast);
    void speakByPhone(boolean isLoud, Phone phone, String text, Observers observers);
}
