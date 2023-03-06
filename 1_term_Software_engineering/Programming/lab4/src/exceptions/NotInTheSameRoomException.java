package exceptions;

import people.Human;

public class NotInTheSameRoomException extends Exception{
    public NotInTheSameRoomException(Human who, String whatToDo, Human toWhom){
        super(who.getName() + " находится в другой комнате и не может " + whatToDo + " " + toWhom.getName());
    }
}
