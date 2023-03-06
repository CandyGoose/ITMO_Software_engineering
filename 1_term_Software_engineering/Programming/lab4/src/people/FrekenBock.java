package people;

import abilities.Helping;
import abilities.Instructing;
import abilities.PhoneActions;
import enums.*;
import exceptions.NotInTheSameRoomException;
import objects.Phone;

public class FrekenBock extends Human implements Helping, Instructing, PhoneActions {
    private Condition condition;
    private Phone phone;

    public FrekenBock() {
        setName("Фрекен Бок");
        setAge(42);
        setGender(Gender.FEMALE);
        setHouseRoom(HouseRooms.FIRST_ROOM);
    }

    @Override
    public void helpToWith(Human toHuman, HelpType helpType) throws NotInTheSameRoomException {
        if (!toHuman.getHouseRoom().equals(getHouseRoom())) {
            throw new NotInTheSameRoomException(this, "помогать", toHuman);
        } else {
            setCondition(Condition.HELPING);
            String output = toHuman.getName() + "у " + helpType.getWhatHelp();
            System.out.println(getName() + " помогала " + output);
            addPerformedActions("Помогала " + output);
        }
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void instructJulius(Instructions instruction, Julius who, Observers observers) throws NotInTheSameRoomException {
        if (!getHouseRoom().equals(who.getHouseRoom())) {
            throw new NotInTheSameRoomException(this, "наставлять", who);
        } else {
            setCondition(Condition.INSTRUCTING);
            String output = who.getName() + "а, чтобы он " + instruction.getWhichInstruction();
            System.out.println(getName() + " наставляла " + output);
            observers.notifyObservers(this,
                    HearType.INSTRUCTIONS.getWhatType() + " " + getName()
                            + " " + who.getName() + "у, чтобы тот",
                    instruction.getWhichInstruction());
            addPerformedActions("Наставляла " + output);
        }
    }


    @Override
    public void walkToPhone(Phone phone, boolean isFast) {
        String went = isFast ? "кинулась " : "подошла ";
        String output = went + "к " + phone.getPhoneType().getWhatPhone();
        addPerformedActions(output);
        System.out.println(getName() + " " + output);
    }

    @Override
    public void speakByPhone(boolean isLoud, Phone phone, String text, Observers observers) {
        String loud = isLoud ? "громкий " : "";
        setCondition(Condition.SPEAKING_BY_PHONE);
        String output = loud + HearType.SPEECH.getWhatType()
                + " по " + phone.getPhoneType().getWhatPhone() + ": " + text;
        System.out.println(getName() + " начала " + output);
        addPerformedActions("Начала " + output);
        observers.notifyObservers(this,
                loud + HearType.SPEECH.getWhatType() + " " + getName()
                        + " по " + phone.getPhoneType().getWhatPhone() + ":",
                text);
    }
}
