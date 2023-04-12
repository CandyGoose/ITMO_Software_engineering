package exceptions;

public class DelPerformedActionsException extends RuntimeException{
    public DelPerformedActionsException(){
        super("Ничего не получится, ведь прошлого уже не вернуть.");
    }
}
