//Малыш покачал головой. Ведь она правда была не у него, а лежала в ящике стола,
//оправдывался он потом перед Карлсоном за свой ответ.
//Впрочем, дядя Юлиус странным образом не проявил в тот день обычного интереса к газете.
//Его мысли были явно заняты чем-то другим, чем-то очень приятным, должно быть,
//потому что у него был непривычно счастливый вид. И потому ему надо было идти к доктору.
//В последний раз. Через несколько часов он уезжает к себе домой, в Вестергетланд.
//Фрекен Бок помогала ему укладывать вещи, и Малыш и Карлсон слышали,
//как она его попутно наставляла: чтобы он не забыл застегнуть верхнюю пуговицу на пальто,
//а то еще продует, и чтобы он осторожно переходил улицу, и чтобы не курил натощак!
//Но этот день странным образом оказался полон сюрпризов. Не успел дядя Юлиус уйти,
//как фрекен Бок кинулась к телефону. И она говорила так громко,
//что Малыш и Карлсон невольно услышали весь ей разговор.
//Карлсон глядел на Малыша широко раскрытыми глазами. Но Малыш ничего не слышал про такое лекарство.
//Карлсон сочувственно вздохнул, а когда дядя Юлиус вернулся от доктора,
//он подошел к нему и молча сунул в руку пятиэровую монетку.

import abilities.Sighing;
import enums.HelpType;
import enums.HumanView;
import enums.Instructions;
import enums.PhoneTypes;
import exceptions.CreatedMoreThanOneException;
import exceptions.NotInTheSameRoomException;
import objects.*;
import other.Day;
import other.Thoughts;
import people.*;

public class Story {
    public static void main(String[] args) throws CreatedMoreThanOneException, NotInTheSameRoomException {
        Kid kid = new Kid();
        Carlson carlson = new Carlson();
        Julius julius = new Julius();
        FrekenBock frekenBock = new FrekenBock();
        Newspaper newspaper = new Newspaper();
        Box box = new Box();
        box.addItemsInBox(newspaper);
        Table table = new Table(box);
        Observers observers = new Observers();
        observers.addObserver(kid, carlson);
        Phone phone = new Phone(PhoneTypes.PHONE, "newModel");

        kid.shakeHead();
        kid.isPersonHasNewspaper();
        box.isItemInsideBox(newspaper);
        kid.apologise(carlson);
        julius.setInterestedInNewspaper(false);
        julius.setThatStrange(true);
        Day day = new Day();
        julius.showInterest(day, newspaper);
        Thoughts juliusThoughts = new Thoughts(true, true);
        julius.setJuliusThoughts(juliusThoughts);
        julius.setView(HumanView.HAPPY, true);
        julius.printJuliusThoughts();
        julius.needToWalkTo("к доктору");
        julius.leavingToHome(3, "Вестергетланд");
        frekenBock.helpToWith(julius, HelpType.PACK_THINGS);
        frekenBock.instructJulius(Instructions.FASTEN_THE_TOP_BUTTON, julius, observers);
        frekenBock.instructJulius(Instructions.CAREFULLY_CROSS_THE_STREET, julius, observers);
        frekenBock.instructJulius(Instructions.DO_NOT_SMOKE_ON_AN_EMPTY_STOMACH, julius, observers);
        day.setDayFullOfSurprises();
        julius.goAwayFromHome(false);
        frekenBock.walkToPhone(phone, true);
        frekenBock.speakByPhone(true, phone, "что-то говорила", observers);
        carlson.lookAt(kid, true);
        kid.isHumanHearAboutThat(false, "лекарство");

        Sighing sighing  = (Human who) -> {
            System.out.println(who.getName() + " сочувственно вздохнул");
            who.addPerformedActions("Cочувственно вздохнул");
        };
        sighing.sigh(carlson);

        julius.backHome("от Доктора");
        julius.walkToHuman(kid);
        Coin coin = new Coin(5);
        julius.putMoneyInHand(kid, coin, true);  
    }
}
