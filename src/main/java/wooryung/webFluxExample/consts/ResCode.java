package wooryung.webFluxExample.consts;

public enum ResCode {
    SUCCESS(0), UNKNOWN(-99);
    private final int value;
    ResCode(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
