package org.example.jdbcdatenvisualisierung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

class Continent {
    String name;
    int population;
}

public class JDBCApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        ArrayList<Continent> continents = new ArrayList<>();
        String url = "jdbc:postgresql://xserv:5432/world2";
        String user = "reader";
        String password = "reader";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT country.continent, SUM(country.population) AS bevoelkerung\n" +
                            "FROM country\n" +
                            "GROUP BY country.continent;");

            while (rs.next()) {
                Continent c = new Continent();
                c.name = rs.getString("continent");
                c.population = rs.getInt("bevoelkerung");
                continents.add(c);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setTitle("Population by Continent");
        barChart.getXAxis().setLabel("Continent");
        barChart.getYAxis().setLabel("Population");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (Continent continent : continents) {
            dataSeries.getData().add(new XYChart.Data<>(continent.name, continent.population));
        }
        barChart.getData().add(dataSeries);

        FXMLLoader fxmlLoader = new FXMLLoader(JDBCApplication.class.getResource("jdbcview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("World 2 DB Visualization");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
