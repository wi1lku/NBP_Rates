package start;

import data.DataGetter;
import data.DataPlot;
import data.DataTableCurrency;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.TreeMap;

public class Another extends Application {

    DataTableCurrency[] testTable = DataGetter.getTableCurrencyData(LocalDate.of(2021, 10, 11));
    LocalDate dayFrom = LocalDate.now();
    LocalDate dayTo = LocalDate.now();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis,yAxis);

    // questions - czemu nie działa z LocalDate xd
    // idk czy to działa ogólnie, bo idk jak to sprawdzić tbh
    // można dodać jeszcze wyszarzenie daty


    public static void main(String[] args) {
        launch(args);
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

    public int dateToInt(LocalDate date){
        return date.getYear()*10000+date.getMonthValue()*100+date.getDayOfMonth();
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane mainPane = new AnchorPane();


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
        dateFromPick.valueProperty().addListener((ov, oldValue, newValue) -> {
            dayFrom = newValue;
            System.out.println(dayFrom);
        });
        dateToPick.valueProperty().addListener((ov, oldValue, newValue) -> {
            dayTo = newValue;
            System.out.println(dayTo);
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
                            lineChart.getData().remove(i.getCode());
                        }else{
                            System.out.println(i.getCode());
                            lineChart.getData().add(series(i.getCode(), dayFrom, dayTo));
                        }
                    }
            );
            downControl.getChildren().add(checkBox);
        }

        CheckBox checkBox = new CheckBox();
        checkBox.setText("Gold");
        downControl.getChildren().add(checkBox);


        // Chart

        xAxis.setLabel("Date");
        yAxis.setLabel("Value");
        //creating the chart
        rightControl.getChildren().add(lineChart);


        leftControl.getItems().addAll(upControl, downControl);
        splitPane.getItems().addAll(leftControl, rightControl);

        mainPane.getChildren().add(splitPane);
        Scene scene = new Scene(mainPane);

        stage.setScene(scene);
        stage.setTitle("Chart");
        stage.show();
    }
}
