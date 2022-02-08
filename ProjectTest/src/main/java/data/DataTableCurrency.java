package data;

public class DataTableCurrency {

    private final String name; // full name of currency
    private final String code; // code of currency
    private final float bid; // sell value of currency
    private final float ask; // buy value of currency

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
