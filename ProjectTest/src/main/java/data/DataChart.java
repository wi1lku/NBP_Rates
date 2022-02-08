package data;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class DataChart {

    private final List<LocalDate> dates; // date of quotation
    private final List<Float> values; // value of quotation

    public DataChart() {
        dates = new LinkedList<>();
        values = new LinkedList<>();
    }

    public void appendToDates(LocalDate date){
        dates.add(date);
    }

    public void appendToValues(float val){
        values.add(val);
    }

    public void addList(DataChart list){
        this.dates.addAll(list.dates);
        this.values.addAll(list.values);
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public List<Float> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "dataChart{" + "dates=" + dates + ", values=" + values + '}';
    }
}