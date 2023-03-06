package people;

import abilities.Leaving;
import abilities.PuttingMoney;
import abilities.ShowingInterest;
import abilities.Walkable;
import enums.Gender;
import enums.HouseRooms;
import enums.HumanView;
import exceptions.NegativeOrZeroNumberException;
import objects.Coin;
import other.Day;
import objects.Newspaper;
import other.Thoughts;

public class Julius extends Human implements ShowingInterest, Walkable, Leaving, PuttingMoney {
    private Day day;
    private HumanView juliusView;
    private boolean isThatNormalViewForHim;
    private boolean isInterestedInNewspaper;
    private boolean isThatStrange;
    private boolean isNeedToGo;
    private boolean isAtHome = true;
    private Newspaper newspaper;
    private Thoughts juliusThoughts;
    private String homeCity;

    public Julius() {
        setName("Юлиус");
        setAge(45);
        setGender(Gender.MALE);
        setHouseRoom(HouseRooms.FIRST_ROOM);
    }

    public void printJuliusThoughts() {
        if (juliusThoughts == null) {
            throw new NullPointerException("А мыслей-то и нет!");
        } else {
            String busy = juliusThoughts.isBusy() ? "заняты чем-то " : "не заняты чем-то ";
            String pleasant = juliusThoughts.isPleasant() ? "приятным " : "неприятным ";
            String view = isThatNormalViewForHim ? "непривычно" : "привычно";
            System.out.println("Мысли Юлиуса " + busy + pleasant + "потому что у него " + view + " " + getJuliusView().getView());
        }
    }

    public void setJuliusThoughts(Thoughts juliusThoughts) {
        this.juliusThoughts = juliusThoughts;
    }

    public void setInterestedInNewspaper(boolean interestedInNewspaper) {
        isInterestedInNewspaper = interestedInNewspaper;
    }

    public void setThatStrange(boolean thatStrange) {
        isThatStrange = thatStrange;
    }

    @Override
    public void showInterest(Day day, Newspaper newspaper) {
        this.day = day;
        this.newspaper = newspaper;
        String interest = isInterestedInNewspaper ? "проявил интерес к " : "не проявил интереса к ";
        String thisDay = day == null ? " " : " в этот день ";
        String strange = isThatStrange ? "странным образом" : "";
        String output = strange + thisDay + interest + newspaper.getName();
        System.out.println(getName() + " " + output);
        addPerformedActions(output);
    }

    public boolean IsInterestedInNewspaper() {
        return isInterestedInNewspaper;
    }

    public boolean isThatStrange() {
        return isThatStrange;
    }

    public Day getDay() {
        return day;
    }

    public Newspaper getNewspaper() {
        return newspaper;
    }

    public void setView(HumanView humanView, boolean isThatNormalViewForHim) {
        this.juliusView = humanView;
        this.isThatNormalViewForHim = isThatNormalViewForHim;
    }

    public boolean isThatNormalViewForHim() {
        return isThatNormalViewForHim;
    }

    public HumanView getJuliusView() {
        return juliusView;
    }

    @Override
    public void needToWalkTo(String where) {
        this.isNeedToGo = true;
        System.out.println(getName() + " должен идти " + where);
        addPerformedActions("Должен идти" + where);
    }

    @Override
    public void goAwayFromHome(boolean isManaged) {
        addPerformedActions("Ушел из дома");
        setHouseRoom(HouseRooms.OUTSIDE_HOUSE);
        isAtHome = false;
        if(!isManaged){
            System.out.println(getName() + " не успел уйти из дома как");
        } else {
            System.out.println(getName() + " ушел из дома");
        }
    }

    @Override
    public void backHome(String from) {
        setHouseRoom(HouseRooms.FIRST_ROOM);
        isAtHome = true;
        addPerformedActions("Вернулся домой " + from);
        System.out.println(getName() + " вернулся домой " + from);
    }

    @Override
    public void walkToHuman(Human human) {
        addPerformedActions("Подошел к " + human.getName() + "у");
        System.out.println(getName() + " подошел к " + human.getName() + "у");
    }

    public boolean isNeedToGo() {
        return isNeedToGo;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    @Override
    public void leavingToHome(int time, String city) {
        String whatTime;
        if (time <= 0) {
            throw new NegativeOrZeroNumberException();
        } else if (time == 1) {
            whatTime = "час";
        } else {
            whatTime = "несколько часов";
        }
        setHomeCity(city);
        System.out.println(getName() + " уезжает домой через " + whatTime + ", в " + city);
        addPerformedActions("Уезжает домой через" + whatTime + ", в " + city);
    }

    public String getHomeCity() {
        return homeCity;
    }

    public boolean isAtHome() {
        return isAtHome;
    }

    @Override
    public void putMoneyInHand(Kid kid, Coin coin, boolean isSilently) {
        String silently = isSilently ? "молча " : "";
        kid.getHand().setCoin(coin);
        String output = silently + "сунул в руку " + kid.getName() + "а " + coin.getValue() + "-тиэровую монетку";
        System.out.println(getName() + " " + output);
        addPerformedActions(output);
    }
}
