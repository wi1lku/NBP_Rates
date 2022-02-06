package start;

import data.DataGetter;
import data.DataTableCurrency;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class Controller {

    @FXML
    private CheckBox USD;
    @FXML
    private CheckBox AUD;
    @FXML
    private CheckBox CAD;
    @FXML
    private CheckBox EUR;
    @FXML
    private CheckBox HUF;
    @FXML
    private CheckBox CHF;
    @FXML
    private CheckBox GBP;
    @FXML
    private CheckBox JPY;
    @FXML
    private CheckBox CZK;
    @FXML
    private CheckBox DKK;
    @FXML
    private CheckBox NOK;
    @FXML
    private CheckBox SEK;
    @FXML
    private CheckBox XDR;
    @FXML
    private CheckBox Gold;


    @FXML
    private AnchorPane myAnchorPane;

    //public void chart(){
     //   XYChart.Data dataPoint1 = new XYChart.Data(x-value, y-value);
   //     XYChart.Data dataPoint2 = new XYChart.Data(x-value, y-value);
   //     XYChart.Data dataPoint3 = new XYChart.Data(x-value, y-value);

   //     XYChart.Series series = XYChart.Series;
   //     series.getData().add(dataPoint1);
    //    series.getData().add(dataPoint2);
  //      series.getData().add(dataPoint3);
  //  }

    public void buttonz(){
        DataTableCurrency[] testTable = DataGetter.getTableCurrencyData(LocalDate.now());
        for(DataTableCurrency i:testTable){
            CheckBox checkBox = new CheckBox();
            checkBox.setText(i.getName());
            checkBox.setOnAction((ActionEvent)->{
                System.out.println(checkBox.getText());
            });
            myAnchorPane.getChildren().add(checkBox);
        }
    }

}
