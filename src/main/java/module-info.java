module com.mrcontas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires java.sql;
    requires playwright;


    exports com.mrcontas.javafx;
    opens com.mrcontas.javafx to javafx.fxml;
}