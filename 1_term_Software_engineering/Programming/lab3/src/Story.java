import enums.*;
import other.Day;
import other.Phone;
import people.*;

public class Story {
    public static void main(String[] args) {
        FrekenBock frekenBock = new FrekenBock();
        Kid kid = new Kid();
        Carlson carlson = new Carlson();
        Julius julius = new Julius();
        Observers observers = new Observers();
        observers.addObserver(carlson, kid);

        frekenBock.helpToWith(julius, HelpType.PACK_THINGS);
        frekenBock.instructJulius(Instructions.FASTEN_THE_TOP_BUTTON, julius, observers);
        frekenBock.instructJulius(Instructions.CAREFULLY_CROSS_THE_STREET, julius, observers);
        frekenBock.instructJulius(Instructions.DO_NOT_SMOKE_ON_AN_EMPTY_STOMACH, julius, observers);
        Day day = new Day();
        day.setDayFullOfSurprises();
        julius.walkAway(false);
        Phone phone = new Phone(PhoneTypes.PHONE, "newModel");
        frekenBock.walkToPhone(phone, true);
        frekenBock.speakByPhone(true,phone, "что-то говорила",observers);
    }
}
