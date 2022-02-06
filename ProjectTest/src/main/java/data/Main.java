package data;

import data.DataCalculator;
import data.DataPlot;
import data.DataTableCurrency;
import data.DataGetter;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // TEST currency table
        System.out.println("Test danych do tabeli (waluty):");
        DataTableCurrency[] testTable = DataGetter.getTableCurrencyData(LocalDate.of(2017, 1, 24));
        for(DataTableCurrency curr: testTable){
            System.out.println(curr);
        }

        // TEST gold table
        System.out.println("\nTest danych do tabeli (zloto):");
        System.out.println(DataGetter.getTableGoldData(LocalDate.of(2022, 1, 28)));

        // TEST currency plot
        System.out.println("\nTest danych do wykresu (waluta):");
        DataPlot testCurrency = DataGetter.getPlotCurrencyData("gbp", LocalDate.of(2012, 1,1),
                LocalDate.of(2012, 12, 31));
        System.out.println(testCurrency);

        // Test gold plot
        System.out.println("\nTest danych do wykresu (zloto):");
        DataPlot testGold = DataGetter.getPlotGoldData(LocalDate.of(2014, 1,1),
                LocalDate.of(2014, 12, 31));
        System.out.println(testGold);

        // TEST calculator
        System.out.println("\nTest danych do kalkulatora:");
        DataCalculator testCalculator = DataGetter.getCalculatorData("gbp", "usd", LocalDate.of(2022, 01, 25));
        System.out.println(testCalculator);
    }

}