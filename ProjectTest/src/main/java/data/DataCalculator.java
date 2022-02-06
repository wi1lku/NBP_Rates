package data;

import java.time.LocalDate;

public class DataCalculator {

    private LocalDate date;

    private String nameSell;
    private String codeSell;
    private float valSell;

    private String nameBuy;
    private String codeBuy;
    private float valBuy;

    public DataCalculator() {}

    public void addSell(DataCalculator seller){
        this.nameSell = seller.getNameSell();
        this.valSell = seller.getValSell();
    }

    public void addBuy(DataCalculator buyer){
        this.nameBuy = buyer.getNameBuy();
        this.valBuy = buyer.getValBuy();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNameSell() {
        return nameSell;
    }

    public String getCodeSell() {
        return codeSell;
    }

    public float getValSell() {
        return valSell;
    }

    public String getNameBuy() {
        return nameBuy;
    }

    public String getCodeBuy() {
        return codeBuy;
    }

    public float getValBuy() {
        return valBuy;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNameSell(String nameSell) {
        this.nameSell = nameSell;
    }

    public void setCodeSell(String codeSell) {
        this.codeSell = codeSell;
    }

    public void setValSell(float valSell) {
        this.valSell = valSell;
    }

    public void setNameBuy(String nameBuy) {
        this.nameBuy = nameBuy;
    }

    public void setCodeBuy(String codeBuy) {
        this.codeBuy = codeBuy;
    }

    public void setValBuy(float valBuy) {
        this.valBuy = valBuy;
    }

    @Override
    public String toString() {
        return "dataCalculator{" +
                "date=" + date +
                ", nameSell='" + nameSell + '\'' +
                ", codeSell='" + codeSell + '\'' +
                ", valSell=" + valSell +
                ", nameBuy='" + nameBuy + '\'' +
                ", codeBuy='" + codeBuy + '\'' +
                ", valBuy=" + valBuy +
                '}';
    }
}
