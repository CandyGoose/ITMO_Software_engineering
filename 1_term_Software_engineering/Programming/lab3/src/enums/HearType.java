package enums;

public enum HearType {
    SPEECH("разговор"),
    INSTRUCTIONS("наставления");

    private final String whatType;
    public String getWhatType(){
        return whatType;
    }
    private HearType(String whatType){
        this.whatType = whatType;
    }
}
