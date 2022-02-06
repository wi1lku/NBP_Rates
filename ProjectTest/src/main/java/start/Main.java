package start;

import data.DataGetter;
import data.DataTableCurrency;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainPane = FXMLLoader.load(getClass().getResource("/MainView1.fxml"));
        Scene scene = new Scene(mainPane);

        stage.setScene(scene);
        stage.setTitle("Chart");
        stage.show();
    }
}
