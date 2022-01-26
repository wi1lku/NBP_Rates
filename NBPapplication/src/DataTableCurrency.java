public class DataTableCurrency {

    private String name;
    private String code;
    private float bid;
    private float ask;

    public DataTableCurrency(String name, String code, float bid, float ask) {
        this.name = name;
        this.code = code;
        this.bid = bid;
        this.ask = ask;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public float getBid() {
        return bid;
    }

    public float getAsk() {
        return ask;
    }

    @Override
    public String toString() {
        return "Currency{" + name + ", " + code + ", " + bid + ", " + ask + "}";
    }
}
