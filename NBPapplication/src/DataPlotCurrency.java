import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class DataPlotCurrency {

    private List<LocalDate> dates;
    private List<Float> values;

    public DataPlotCurrency() {
        dates = new LinkedList<>();
        values = new LinkedList<>();
    }

    public void appendToDates(LocalDate date){
        dates.add(date);
    }

    public void appendToValues(float val){
        values.add(val);
    }

    public void addList(DataPlotCurrency list){
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
