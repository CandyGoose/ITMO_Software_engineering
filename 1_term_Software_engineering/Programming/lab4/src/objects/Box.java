package objects;

import java.util.ArrayList;

public class Box {
    ArrayList<Things> items = new ArrayList<>();

    public void getListOfItemsInBox() {
        if(items.size() == 0){
            System.out.println("В ящике нет вещей");
        }else {
            System.out.println("В ящике лежат такие вещи:");
            for (int i = 0; i < items.size(); i++) {
                class Printer {
                    final Things thing;
                    final int iteration;

                    Printer(Things things, int iteration){
                        this.thing = things;
                        this.iteration = iteration;
                    }

                    void printRaw(){
                        System.out.println(iteration + 1 + ". " + thing.getName());
                    }
                }

                Printer printer = new Printer(items.get(i), i);
                printer.printRaw();
            }
        }
    }

    public ArrayList<Things> getItemsInBox() {
        return items;
    }

    public void removeItemsInBox(int index) {
        items.remove(index);
    }

    public void isItemInsideBox(Things things){
        for (Things thing: items) {
            if(thing.getClass().equals(things.getClass())){
                System.out.println(things.getName() + " лежит в ящике стола");
            }
        }
    }

    public void addItemsInBox(Things... things){
        for (Things thing: things) {
            items.add(thing);
            if(thing.getClass().equals(Newspaper.class)){
                ((Newspaper) thing).setBox(this);
            }
        }
    }
}

