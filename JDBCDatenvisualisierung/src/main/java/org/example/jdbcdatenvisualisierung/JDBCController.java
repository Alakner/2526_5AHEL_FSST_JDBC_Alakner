package org.example.jdbcdatenvisualisierung;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class JDBCController {

    @FXML
    private BarChart<String, Number> barChart;

    public void setDataSeries(XYChart.Series<String, Number> series) {
        barChart.getData().clear();
        barChart.getData().add(series);
    }

}