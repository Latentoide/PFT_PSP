module es.alfema.pft {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens es.alfema.pft to javafx.fxml;
    exports es.alfema.pft;
}