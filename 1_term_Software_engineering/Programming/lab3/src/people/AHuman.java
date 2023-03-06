package people;

import abilities.IWalkToRoom;
import enums.Gender;
import enums.HouseRooms;

public abstract class AHuman implements IWalkToRoom {
    private String name;
    private int age;
    private Gender gender;
    private HouseRooms houseRoom;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public HouseRooms getHouseRoom(){
        return houseRoom;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setAge(int age) {
        this.age = age;
    }

    protected void setGender(Gender gender) {
        this.gender = gender;
    }

    protected void setHouseRoom(HouseRooms houseRoom) {
        this.houseRoom = houseRoom;
    }

    public boolean equals(Object o) {
        return ((AHuman) o).getGender().equals(getGender()) &&
                ((AHuman) o).getName().equals(getName()) &&
                ((AHuman) o).getAge() == getAge() &&
                ((AHuman) o).getHouseRoom() == getHouseRoom() &&
                getClass().equals(o.getClass());
    }

    public int hashCode(){
        int hash = 0;
        int houseRoomCoords = getHouseRoom().getX() + getHouseRoom().getY() + getHouseRoom().getZ();
        int gen = getGender().equals(Gender.MALE) ? 1 : 0;
        for (char x: getName().toCharArray()) {
            if(Integer.MAX_VALUE - hash < Character.MAX_VALUE * 75 + 12 + getAge() + gen + houseRoomCoords)
                hash += x*12 + 12 + getAge() + gen + houseRoomCoords;
            else{
                hash -= x*7 + 43 + getAge() + gen + houseRoomCoords;
            }
        }
        return Math.abs(hash) ;
    }

    @Override
    public String toString() {
        return "Это человек по имени: " + getName() + ", пола: " + getGender() + ", возраста: " + getAge() +
                ", ушедший когда-то " + getHouseRoom().getRoomName() + " и не вернувшийся оттуда";
    }


    @Override
    public void walkToRoom(HouseRooms houseRoom) {
        setHouseRoom(houseRoom);
        String gender = getGender().equals(Gender.MALE) ? "ушел" : "ушла";
        System.out.println(getName() + " " + gender + " " + houseRoom.getRoomName());
    }
}
