package start;

import data.DataGetter;
import data.DataTableCurrency;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SecondPage extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane mainPane = new AnchorPane();

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


        mainPane.getChildren().add(table);
        Scene scene = new Scene(mainPane);

        stage.setScene(scene);
        stage.setTitle("Chart");
        stage.show();
    }
}
