package people;

import java.util.ArrayList;

public class Observers {
    private ArrayList<Listeners> listeners = new ArrayList<>();

    public void notifyObservers(AHuman human, String notMixingText, String mixingText){
        for (Listeners listener: listeners) {
            listener.notifyListener(human, notMixingText, mixingText);
        }
    }

    public void addObserver(Listeners... listener){
        for (Listeners value : listener) {
            listeners.add(value);
            System.out.println(value.getName() + " стал новым слушателем");
        }

    }

    public void removeObserverByIndex(int index){
        listeners.remove(index);
    }

    public String getObservers(){
        String ansText = "";
        for (int i = 0; i<listeners.size(); i++) {
            ansText = ansText.concat("Имя слушателя - " + listeners.get(i).getName() + ", индекс - " + i + "\n");
        }
        return ansText;
    }

}
