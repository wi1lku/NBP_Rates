package start;

import data.DataGetter;
import data.DataPlot;
import data.DataTableCurrency;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.TreeMap;

public class Anotherv2 extends Application {

    private LocalDate dayNow = LocalDate.now();
    private DataTableCurrency[] testTable =
            DataGetter.getTableCurrencyData(LocalDate.of(2021, 10, 11));
    private LocalDate dayFrom = LocalDate.of(2022, 01, 03);
    private LocalDate dayTo = LocalDate.now();
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private final TreeMap<String, XYChart.Series<String, Number>> seriesTreeMap = new TreeMap<>();
    private final CheckBox checkBoxG = new CheckBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Chart");
        Tab tab2 = new Tab("Cantor");


        tab1.setContent(page1());
        tab2.setContent(page2());

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene scene = new Scene(tabPane);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("NBP Rates");
        stage.show();
    }

    // default DatePicker
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

    // DatePicker dayToPick
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

                        if(item.isAfter(LocalDate.now())){
                            setDisable(true);
                            setStyle("-fx-background-color: #6a6a6a ;");
                        }
                    }
                };
            }
        };
        return dayCellFactory2;
    }

    // set dayNow to be compliant with data and
    // change dataTableCurrency
    public void changeDate(){
        if(dayNow.getDayOfWeek() == DayOfWeek.SATURDAY){
            dayNow = dayNow.minusDays(1);
            System.out.println(dayNow);
        }else if(dayNow.getDayOfWeek() == DayOfWeek.SUNDAY){
            dayNow = dayNow.minusDays(2);
        }
        testTable = DataGetter.getTableCurrencyData(dayNow);
    }


    // uncheck all checkboxes in a vbox
    public void uncheck(VBox vBox){
        for(Node i:vBox.getChildren()){
            if(i instanceof CheckBox){
                ((CheckBox) i).setSelected(false);
            }
        }
    }

    // create currency data for chart
    public XYChart.Series<String, Number> series(String code, LocalDate dayFrom, LocalDate dayTo){

        DataPlot currency = DataGetter.getPlotCurrencyData(code, dayFrom, dayTo);
        XYChart.Series series1 = new XYChart.Series();

        for(int j = 0; j < currency.getDates().toArray().length; j++){
            series1.getData().add(new XYChart.Data(currency.getDates().get(j).toString(), currency.getValues().get(j)));
        }

        return series1;
    }

    // create gold data for chart
    public XYChart.Series<String, Number> goldSeries(LocalDate dayFrom, LocalDate dayTo){
        DataPlot currency = DataGetter.getPlotGoldData(dayFrom, dayTo);
        XYChart.Series series1 = new XYChart.Series();
        for(int j = 0; j < currency.getDates().toArray().length; j++){
            series1.getData().add(new XYChart.Data(currency.getDates().get(j).toString(), currency.getValues().get(j)));
        }
        return series1;
    }

    // First page
    public Pane page1(){
        AnchorPane mainPane = new AnchorPane();

        SplitPane splitPane = new SplitPane();
        SplitPane leftControl  = new SplitPane();
        AnchorPane rightControl = new AnchorPane();
        leftControl.setOrientation(Orientation.VERTICAL);
        VBox upControl = new VBox();
        VBox downControl = new VBox();

        changeDate();

        // DatePickers
        Label dateFrom = new Label("Date from:");
        Label dateTo = new Label("Date to:");
        DatePicker dateFromPick = new DatePicker();
        DatePicker dateToPick = new DatePicker();
        // Set look
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
        dateFromPick.setDayCellFactory(dayCellFactory);
        dateToPick.setDayCellFactory(dayCellFactory);
        // Add listeners
        dateFromPick.valueProperty().addListener((ov, oldValue, newValue) -> {
            dayFrom = newValue;
            System.out.println(dayFrom);
            lineChart.getData().clear();

            // update dateToPick DatePicker look
            Callback<DatePicker, DateCell> dayCellFactory2 = this.getDayCellFactory2();
            dateToPick.setDayCellFactory(dayCellFactory2);

            // uncheck all
            uncheck(downControl);
            // select gold
            checkBoxG.setSelected(true);

        });
        dateToPick.valueProperty().addListener((ov, oldValue, newValue) -> {
            dayTo = newValue;
            System.out.println(dayTo);
            lineChart.getData().clear();

            // uncheck all
            uncheck(downControl);
            // select gold
            checkBoxG.setSelected(true);
        });

        // Add DatePickers to pane
        upControl.getChildren().add(dateFrom);
        upControl.getChildren().add(dateFromPick);
        upControl.getChildren().add(dateTo);
        upControl.getChildren().add(dateToPick);



        // Add currency checkboxes and listeners
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
                            seriesTreeMap.get(i.getCode()).setName(i.getCode());
                            lineChart.getData().add(seriesTreeMap.get(i.getCode()));

                        }
                    }
            );
            downControl.getChildren().add(checkBox);
        }
        // Add gold checkbox and listener
        checkBoxG.setText("Gold");
        checkBoxG.selectedProperty().addListener(
                (ov, old_val, new_val) -> {
                    if(old_val){
                        System.out.println("Delete");
                        lineChart.getData().remove(seriesTreeMap.get("Gold"));
                    }else{
                        System.out.println("Gold");
                        seriesTreeMap.put("Gold", goldSeries(dayFrom, dayTo));
                        seriesTreeMap.get("Gold").setName("Gold");
                        lineChart.getData().add(seriesTreeMap.get("Gold"));
                    }
                }
        );
        downControl.getChildren().add(checkBoxG);
        checkBoxG.setSelected(true);


        // Set up chart
        xAxis.setLabel("Date");
        yAxis.setLabel("Value");
        lineChart.setCreateSymbols(false);
        rightControl.getChildren().add(lineChart);


        leftControl.getItems().addAll(upControl, downControl);
        splitPane.getItems().addAll(leftControl, rightControl);

        mainPane.getChildren().add(splitPane);

        return mainPane;
    }

    // Second page
    public Pane page2(){
        AnchorPane mainPane = new AnchorPane();

        GridPane table = new GridPane();

        TextField dateText = new TextField("Date: " + dayNow);
        dateText.setEditable(false);

        // Prepare data
        changeDate();

        // Prepare DatePicker
        DatePicker datePicker = new DatePicker();
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            LocalDate day = newValue;
            DataTableCurrency[] newTable = DataGetter.getTableCurrencyData(day);
            int j = 3;
            for(DataTableCurrency i:newTable){
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
            dateText.setText("Date: " + day);
        });

        // Add labels and DatePicker
        table.add(dateText, 0, 0);
        table.add(datePicker, 2, 0);
        table.add(new Label("Name:"), 0, 1);
        table.add(new Label("Code:"), 1, 1);
        table.add(new Label("Sell:"), 2, 1);
        table.add(new Label("Buy:"), 3, 1);

        // Create table
        int j = 2;
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


        mainPane.getChildren().add(table);

        return mainPane;
    }


}
