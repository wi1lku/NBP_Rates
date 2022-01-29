import java.time.LocalDate;

public class DataTableGold {

    private LocalDate date;
    private float val;

    public DataTableGold(LocalDate date, float val) {
        this.date = date;
        this.val = val;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "Gold{" + date + ", " + val + '}';
    }
}
