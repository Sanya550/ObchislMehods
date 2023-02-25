module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javaluator;
    requires MathParser.org.mXparser;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}