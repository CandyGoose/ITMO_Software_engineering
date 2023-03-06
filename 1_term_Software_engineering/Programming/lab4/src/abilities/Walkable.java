package abilities;

import people.Human;

public interface Walkable {
    void needToWalkTo(String where);
    void goAwayFromHome(boolean isManaged);
    void backHome(String from);
    void walkToHuman(Human human);
}
