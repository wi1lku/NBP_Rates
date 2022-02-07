package start;

import data.DataGetter;
import data.DataTableCurrency;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class SecondPage extends Application {

    private LocalDate dayNow = LocalDate.now();
    private DataTableCurrency[] testTable =
            DataGetter.getTableCurrencyData(LocalDate.of(2021, 10, 11));

    public void changeDate(){
        if(dayNow.getDayOfWeek() == DayOfWeek.SATURDAY){
            dayNow = dayNow.minusDays(1);
            System.out.println(dayNow);
        }else if(dayNow.getDayOfWeek() == DayOfWeek.SUNDAY){
            dayNow = dayNow.minusDays(2);
        }
        testTable = DataGetter.getTableCurrencyData(dayNow);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane mainPane = new AnchorPane();

        GridPane table = new GridPane();

        // Prepare data
        changeDate();

        // Add labels

        table.add(new Label("Date:" + dayNow), 0, 0);
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


        Scene scene = new Scene(mainPane);

        stage.setScene(scene);
        stage.setTitle("Chart");
        stage.show();
    }
}
