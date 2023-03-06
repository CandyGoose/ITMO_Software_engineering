/*
Фрекен Бок помогала ему укладывать вещи, и Малыш и Карлсон слышали,
как она его попутно наставляла: чтобы он не забыл застегнуть верхнюю пуговицу на пальто,
а то еще продует, и чтобы он осторожно переходил улицу, и чтобы не курил натощак!
Но этот день странным образом оказался полон сюрпризов.
Не успел дядя Юлиус уйти, как фрекен Бок кинулась к телефону.
И она говорила так громко, что Малыш и Карлсон невольно услышали весь ей разговор.
*/

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
