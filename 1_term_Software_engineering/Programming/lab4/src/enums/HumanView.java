package enums;

public enum HumanView {
    HAPPY("счастливый вид"),
    BAD("несчастный вид");

    private final String view;

    private HumanView(String view){
        this.view = view;
    }

    public String getView() {
        return view;
    }
}
