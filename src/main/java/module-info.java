module com.mathias.womenstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.mathias.womenstore to javafx.fxml;
    exports com.mathias.womenstore;
}