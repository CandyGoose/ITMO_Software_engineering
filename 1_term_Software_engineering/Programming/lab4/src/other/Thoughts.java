package other;

public class Thoughts {
    private boolean isBusy;
    private boolean isPleasant;

    public Thoughts(boolean isBusy, boolean isPleasant) {
        this.isBusy = isBusy;
        this.isPleasant = isPleasant;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public boolean isPleasant() {
        return isPleasant;
    }

    public void setPleasant(boolean pleasant) {
        isPleasant = pleasant;
    }
}
