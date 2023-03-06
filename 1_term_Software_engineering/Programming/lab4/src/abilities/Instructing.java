package abilities;

import enums.Instructions;
import exceptions.NotInTheSameRoomException;
import people.Julius;
import people.Observers;

public interface Instructing {
    //Исключение, если находятся в разных комнатах
    void instructJulius(Instructions instruction, Julius who, Observers observers) throws NotInTheSameRoomException;
}
