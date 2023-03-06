package abilities;

import enums.Instructions;
import people.Julius;
import people.Observers;

public interface IInstruct {
    public void instructJulius(Instructions instruction, Julius who, Observers observers);
}
