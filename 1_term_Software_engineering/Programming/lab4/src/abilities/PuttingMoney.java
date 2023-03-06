package abilities;

import objects.Coin;
import people.Kid;

public interface PuttingMoney {
    void putMoneyInHand(Kid kid, Coin coin, boolean isSilently);
}
