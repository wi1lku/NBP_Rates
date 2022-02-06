module javaFX {
    requires java.net.http;
    requires org.json;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    exports start to javafx.graphics;
}