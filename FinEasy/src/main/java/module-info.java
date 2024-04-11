module org.example.fineasy {
    requires javafx.controls;
    requires javafx.fxml;

    // 允许javafx.fxml模块使用org.example.fineasy.controllers包中的类

    // 打开包以允许FXML加载器反射访问
    opens org.example.fineasy.models to javafx.base, javafx.fxml;
    opens org.example.fineasy.controllers to javafx.fxml;
    opens org.example.fineasy to javafx.fxml;
    exports org.example.fineasy;
    exports org.example.fineasy.controllers;
    opens org.example.fineasy.service to javafx.base, javafx.fxml;
    opens org.example.fineasy.entity to javafx.base, javafx.fxml;
}