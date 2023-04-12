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
