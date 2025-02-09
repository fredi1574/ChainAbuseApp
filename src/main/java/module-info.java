module com.example.ChainAbuse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.apache.poi.ooxml;
    requires org.json;
    requires unirest.java;

    opens ChainAbuse to javafx.fxml, javafx.controls;
    exports ChainAbuse;
}