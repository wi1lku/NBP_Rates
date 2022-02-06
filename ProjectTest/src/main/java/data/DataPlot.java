package data;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class DataPlot {

    private List<LocalDate> dates;
    private List<Float> values;

    public DataPlot() {
        dates = new LinkedList<>();
        values = new LinkedList<>();
    }

    public void appendToDates(LocalDate date){
        dates.add(date);
    }

    public void appendToValues(float val){
        values.add(val);
    }

    public void addList(DataPlot list){
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
        return "dataPlotCurrency{" +
                "dates=" + dates +
                ", values=" + values +
                '}';
    }
}