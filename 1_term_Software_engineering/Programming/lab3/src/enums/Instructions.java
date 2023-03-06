package enums;

public enum Instructions {
    FASTEN_THE_TOP_BUTTON("не забыл застегнуть верхнюю пуговицу на пальто, а то продует!"),
    CAREFULLY_CROSS_THE_STREET("осторожно переходил улицу!"),
    DO_NOT_SMOKE_ON_AN_EMPTY_STOMACH("не курил натощак!");

    private final String whichInstruction;

    public String getWhichInstruction(){
        return whichInstruction;
    }

    private Instructions(String whichInstruction) {
        this.whichInstruction = whichInstruction;
    }
}

