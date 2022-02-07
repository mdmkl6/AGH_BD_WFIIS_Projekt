module project.project_v1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens project.project_v1 to javafx.fxml;
    exports project.project_v1;
}