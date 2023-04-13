package enums;

public enum HelpType {
    PACK_THINGS("укладывать вещи"),
    GET_DRESSED("одеваться");

    private final String whatHelp;
    public String getWhatHelp(){
        return whatHelp;
    }
    private HelpType(String whatHelp){
        this.whatHelp = whatHelp;
    }
}
