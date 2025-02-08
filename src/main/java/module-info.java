module com.example.aiproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.aiproject to javafx.fxml;
    exports com.example.aiproject;
}