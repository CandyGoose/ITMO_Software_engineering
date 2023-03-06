package exceptions;

public class DelPerformedActionsException extends RuntimeException{
    public DelPerformedActionsException(){
        super("Отличная попытка, но у тебя ничего не получится, ведь прошлого уже не вернуть, такова реальность!");
    }
}
