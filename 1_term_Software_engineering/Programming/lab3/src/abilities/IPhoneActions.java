package abilities;

import other.Phone;
import people.Observers;

public interface IPhoneActions {
    public void walkToPhone(Phone toPhone, boolean isFast);
    public void speakByPhone(boolean isLoud, Phone phone, String text, Observers observers);
}
