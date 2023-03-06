package other;

public class Day {
    private boolean isDayFullOfSurprises;

    public boolean isDayFullOfSurprises(){
        return isDayFullOfSurprises;
    }

    public void setDayFullOfSurprises(){
        if(!isDayFullOfSurprises()) {
            isDayFullOfSurprises = true;
            System.out.println("День странным образом оказался полон сюрпризов!");
        } else {
            System.out.println("День уже давно полон сюрпризов, куда еще.");
        }
    }
}
