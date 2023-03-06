package people;

import abilities.LookingAt;
import enums.Gender;
import enums.HouseRooms;

public class Carlson extends Listeners implements LookingAt {
    private final Eyes eyes;

    public Carlson() {
        super("Карлсон", 33, Gender.MALE, HouseRooms.FIRST_ROOM);
        eyes = new Eyes(false);
    }

    @Override
    public void lookAt(Human atWho, boolean isEyesWideOpened) {
        eyes.setWideOpened(isEyesWideOpened);
        String openedEyes = eyes.isWideOpened ? " широко раскрытыми глазами" : "";
        String output = atWho.getName() + "а" + openedEyes;
        System.out.println(getName() + " смотрел на " + output);
        addPerformedActions("Смотрел на " + output);
    }

    private static class Eyes {
        private boolean isWideOpened;

        public Eyes(boolean isWideOpened) {
            this.isWideOpened = isWideOpened;
        }

        public boolean isWideOpened() {
            return isWideOpened;
        }

        public void setWideOpened(boolean wideOpened) {
            isWideOpened = wideOpened;
        }
    }
}
