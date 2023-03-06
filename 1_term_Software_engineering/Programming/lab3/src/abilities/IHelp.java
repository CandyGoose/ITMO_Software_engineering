package abilities;

import enums.HelpType;
import people.AHuman;

public interface IHelp {
    public void helpToWith(AHuman toHuman, HelpType helpType);
}
