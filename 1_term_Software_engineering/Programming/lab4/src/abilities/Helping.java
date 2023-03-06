package abilities;

import enums.HelpType;
import exceptions.NotInTheSameRoomException;
import people.Human;

public interface Helping {
    //Исключение, если находятся в разных комнатах
    void helpToWith(Human toHuman, HelpType helpType) throws NotInTheSameRoomException;
}
