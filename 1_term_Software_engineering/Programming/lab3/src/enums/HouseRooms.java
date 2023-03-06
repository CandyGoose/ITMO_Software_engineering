package enums;

public enum HouseRooms {
    FIRST_ROOM("в первую комнату",0,0,0),
    SECOND_ROOM("во вторую комнату",0,1,0),
    THIRD_ROOM("в третью комнату",0,0,1),
    FOURTH_ROOM("в четвертую комнату",0,1,1),
    SPA_ROOM("в СПА",1,0,0),
    KITCHEN("на кухне",1,1,0),
    FIRST_BATHROOM("в первую ванную комнату",1,2,0),
    SECOND_BATHROOM("во вторую ванную комнату",1,2,1),
    LIBRARY("в библиотеку",1,0,1),
    BEDROOM("в спальню",0,2,1),
    HALL("в холл",1,1,1),
    GYM("в спорт зал",1,2,2),
    GAME_ROOM("в игровой.",1,1,2),
    REST_ROOM("в комнату отдыха",0,0,2),
    GARAGE("в гараж",0,2,0);

    private final String roomName;
    private final int x;
    private final int y;
    private final int z;

    private HouseRooms(String roomName, int x, int y, int z) {
        this.roomName = roomName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
