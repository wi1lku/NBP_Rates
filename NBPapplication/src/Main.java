import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // TEST Currency table
        System.out.println("Test danych do tabeli:");
        DataTableCurrency[] testTable = DataGetter.getTableData(LocalDate.of(2017, 1, 24));
        for(DataTableCurrency curr: testTable){
            System.out.println(curr);
        }

        // TEST Currency plot
        System.out.println("\nTest danych do wykresu:");
        DataPlotCurrency testCurrency = DataGetter.getPlotCurrencyData("gbp", LocalDate.of(2012, 1,1),
                LocalDate.of(2012, 12, 31));
        System.out.println(testCurrency);

        // TEST calculator
        System.out.println("\nTest danych do kalkulatora:");
        DataCalculator testCalculator = DataGetter.getCalculatorData("gbp", "usd", LocalDate.of(2022, 01, 25));
        System.out.println(testCalculator);
    }

}
