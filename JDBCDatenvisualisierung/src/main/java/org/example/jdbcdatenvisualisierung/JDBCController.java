package org.example.jdbcdatenvisualisierung;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;

public class JDBCController {

    @FXML
    private BarChart<String, Number> barChart;

    public void setDataSeries(XYChart.Series<String, Number> series) {
        barChart.getData().clear();
        barChart.getData().add(series);
    }

    public void createCheckboxes(String [] continents) {
        // TODO Wittner: Implementieren Sie die Methode zum Erstellen von Checkboxes für Kontinente
        for (String continent : continents) {
            // Erstellen Sie eine Checkbox für jeden Kontinent
            // Fügen Sie die Checkbox zur GUI hinzu
            CheckBox checkBox = new CheckBox(continent);
            checkBox.setSelected(true);
            checkBox.setVisible(true);
            // Implementieren Sie die Logik zum Ein- und Ausblenden der Kontinente im BarChart
            // Listener hinzufügen (https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/checkbox.htm#CHDFEJCD)
            checkBox.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov,
                     Boolean old_val, Boolean new_val) -> {
                        checkBox.setSelected(new_val);
                        checkBox.setVisible(new_val);
                    });
        }
    }
}