package people;

import enums.Gender;
import enums.HouseRooms;

public class Listeners extends Human {
    protected Listeners(String name, int age, Gender gender, HouseRooms houseRoom) {
        setName(name);
        setAge(age);
        setGender(gender);
        setHouseRoom(houseRoom);
    }

    public void notifyListener(Human human, String notMixingText, String mixingText) {
        int difX = Math.abs(human.getHouseRoom().getX() - getHouseRoom().getX());
        int difY = Math.abs(human.getHouseRoom().getY() - getHouseRoom().getY());
        int difZ = Math.abs(human.getHouseRoom().getZ() - getHouseRoom().getZ());
        int dif = difX + difY + difZ;
        if (dif <= 1) {
            String gender = Gender.MALE.equals(getGender()) ? "услышал" : "услышала";
            String mixed = getHearText(mixingText, difX, difY, dif);
            System.out.println(getName() + " " + gender + " " + notMixingText + " " + mixed);
        }
    }

    public String getHearText(String hearText, int difX, int difY, int dif) {
        String[] textWords = hearText.split(" ");
        double chance = 0d;
        if(dif == 0){
            chance = 0d;
        } else if(difX == 1 || difY == 1){
            chance = 0.3d;
        } else {
            chance = 0.5d;
        }

        String text = "";
        for (int i = 0; i < textWords.length; i++) {
            if(Math.random() < chance){
                textWords[i] = "";
            } else {
                text = text.concat(textWords[i] + " ");
            }
        }
        return text;
    }
}
