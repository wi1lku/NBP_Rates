package start;

import data.DataGetter;
import data.DataPlot;
import data.DataTableCurrency;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.TreeMap;

public class Anotherv2 extends Application {

    DataTableCurrency[] testTable = DataGetter.getTableCurrencyData(LocalDate.of(2021, 10, 11));
    LocalDate dayFrom = LocalDate.now();
    LocalDate dayTo = LocalDate.now();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis,yAxis);
    TreeMap<String, XYChart.Series<String, Number>> seriesTreeMap = new TreeMap<String, XYChart.Series<String, Number>>();

    // popraw legende
    // czemu xaxis nie dziala dla jednego wykresu
    // sprawdź buy sell


    public static void main(String[] args) {
        launch(args);
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable Saturday and Sunday.
                        if (item.getDayOfWeek() == DayOfWeek.SATURDAY //
                                || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
                            setDisable(true);
                            setStyle("-fx-background-color: #6a6a6a ;");
                        }

                        if(item.isAfter(LocalDate.now())){
                            setDisable(true);
                            setStyle("-fx-background-color: #6a6a6a ;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    private Callback<DatePicker, DateCell> getDayCellFactory2() {

        final Callback<DatePicker, DateCell> dayCellFactory2 = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable Saturday and Sunday.
                        if (item.getDayOfWeek() == DayOfWeek.SATURDAY //
                                || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
                            setDisable(true);
                            setStyle("-fx-background-color: #6a6a6a ;");
                        }

                        if(item.isBefore(dayFrom)){
                            setDisable(true);
                            setStyle("-fx-background-color: #6a6a6a ;");
                        }
                    }
                };
            }
        };
        return dayCellFactory2;
    }

    // create data for chart
    public XYChart.Series<String, Number> series(String code, LocalDate dayFrom, LocalDate dayTo){

        //TreeMap<String, XYChart.Series<Number, Number>> data = new TreeMap<String, XYChart.Series<Number, Number>>();
        DataPlot currency = DataGetter.getPlotCurrencyData(code, dayFrom, dayTo);
        XYChart.Series series1 = new XYChart.Series();
        for(int j = 0; j < currency.getDates().toArray().length; j++){
            series1.getData().add(new XYChart.Data(currency.getDates().get(j).toString(), currency.getValues().get(j)));
        }

        //data.put(code, series1);
        //return data;
        return series1;
    }

    public XYChart.Series<String, Number> goldSeries(LocalDate dayFrom, LocalDate dayTo){
        DataPlot currency = DataGetter.getPlotGoldData(dayFrom, dayTo);
        XYChart.Series series1 = new XYChart.Series();
        for(int j = 0; j < currency.getDates().toArray().length; j++){
            series1.getData().add(new XYChart.Data(currency.getDates().get(j).toString(), currency.getValues().get(j)));
        }
        return series1;
    }


    @Override
    public void start(Stage stage) throws Exception {

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Chart");
        Tab tab2 = new Tab("Cantor");



        // FIRST SCENE

        AnchorPane mainPane1 = new AnchorPane();
        
        SplitPane splitPane = new SplitPane();
        SplitPane leftControl  = new SplitPane();
        AnchorPane rightControl = new AnchorPane();
        leftControl.setOrientation(Orientation.VERTICAL);
        VBox upControl = new VBox();
        VBox downControl = new VBox();

        // DatePickers controls
        Label dateFrom = new Label("Date from:");
        Label dateTo = new Label("Date to:");
        DatePicker dateFromPick = new DatePicker();
        DatePicker dateToPick = new DatePicker();
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
        dateFromPick.setDayCellFactory(dayCellFactory);
        dateToPick.setDayCellFactory(dayCellFactory);
        dateFromPick.valueProperty().addListener((ov, oldValue, newValue) -> {
            dayFrom = newValue;
            System.out.println(dayFrom);
            lineChart.getData().clear();
            Callback<DatePicker, DateCell> dayCellFactory2 = this.getDayCellFactory2();
            dateToPick.setDayCellFactory(dayCellFactory2);
        });
        dateToPick.valueProperty().addListener((ov, oldValue, newValue) -> {
            dayTo = newValue;
            System.out.println(dayTo);
            lineChart.getData().clear();
        });

        // add DatePickers
        upControl.getChildren().add(dateFrom);
        upControl.getChildren().add(dateFromPick);
        upControl.getChildren().add(dateTo);
        upControl.getChildren().add(dateToPick);



        // Add checkboxes and listeners
        for(DataTableCurrency i:testTable){
            CheckBox checkBox = new CheckBox();
            checkBox.setText(i.getCode());
            checkBox.selectedProperty().addListener(
                    (ov, old_val, new_val) -> {
                        if(old_val){
                            System.out.println("Delete");
                            lineChart.getData().remove(seriesTreeMap.get(i.getCode()));
                        }else{
                            System.out.println(i.getCode());
                            seriesTreeMap.put(i.getCode(), series(i.getCode(), dayFrom, dayTo));
                            lineChart.getData().add(seriesTreeMap.get(i.getCode()));
                        }
                    }
            );
            downControl.getChildren().add(checkBox);
        }

        CheckBox checkBox = new CheckBox();
        checkBox.setText("Gold");
        checkBox.selectedProperty().addListener(
                (ov, old_val, new_val) -> {
                    if(old_val){
                        System.out.println("Delete");
                        lineChart.getData().remove(seriesTreeMap.get("Gold"));
                    }else{
                        System.out.println("Gold");
                        seriesTreeMap.put("Gold", goldSeries(dayFrom, dayTo));
                        lineChart.getData().add(seriesTreeMap.get("Gold"));
                    }
                }
        );
        downControl.getChildren().add(checkBox);


        // Chart

        xAxis.setLabel("Date");
        yAxis.setLabel("Value");
        lineChart.setCreateSymbols(false);
        rightControl.getChildren().add(lineChart);


        leftControl.getItems().addAll(upControl, downControl);
        splitPane.getItems().addAll(leftControl, rightControl);

        mainPane1.getChildren().add(splitPane);



        // SCENE 2

        AnchorPane mainPane2 = new AnchorPane();

        GridPane table = new GridPane();

        // Add labels
        table.add(new Label("Name:"), 0, 0);
        table.add(new Label("Code:"), 1, 0);
        table.add(new Label("Sell:"), 2, 0);
        table.add(new Label("Buy:"), 3, 0);

        // Prepare data
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        DataTableCurrency[] testTable = DataGetter.getTableCurrencyData(LocalDate.of(2021, 10, 11));

        // Create table
        int j = 1;
        for(DataTableCurrency i:testTable){
            TextField name = new TextField(i.getName());
            TextField code = new TextField(i.getCode());
            TextField bid = new TextField(Float.toString(i.getBid()));
            TextField ask = new TextField(Float.toString(i.getAsk()));
            name.setEditable(false);
            code.setEditable(false);
            bid.setEditable(false);
            ask.setEditable(false);
            table.add(name, 0, j);
            table.add(code, 1, j);
            table.add(bid, 2, j);
            table.add(ask, 3, j);
            j += 1;
        }


        mainPane2.getChildren().add(table);

        tab1.setContent(mainPane1);
        tab2.setContent(mainPane2);
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene scene = new Scene(tabPane);

        stage.setScene(scene);
        stage.setTitle("NBP Rates");
        stage.show();
    }
}
