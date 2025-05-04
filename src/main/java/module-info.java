module org.example.snakefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.snakefx to javafx.fxml;
    exports org.example.snakefx;
    exports org.example.snakefx.Controller;
    opens org.example.snakefx.Controller to javafx.fxml;
}