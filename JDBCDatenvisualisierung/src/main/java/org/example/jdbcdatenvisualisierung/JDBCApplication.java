package org.example.jdbcdatenvisualisierung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.sql.*;


public class Continent from ArrayList<Continent>{
    String name;
    int population;
}

public class JDBCApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Continent continents = new Continent();
        String url = "jdbc:postgresql://xserv:5432/world2";
        String user = "reader";
        String password = "reader";

        try {

            // 1. Establish Connection
            Connection con = DriverManager.getConnection(url, user, password);

            // 3. Create Statement
            Statement stmt = con.createStatement();

            // 4. Execute Query
            ResultSet rs = stmt.executeQuery(
                    "SELECT country.continent, SUM(country.population) AS bevoelkerung\n" +
                            "FROM country\n" +
                            "GROUP BY country.continent;");

            // 5. Process Results
            while (rs.next()) {
                continents.add(new Continent());
                continents.get(continents.size() - 1).name = rs.getString("continent");
                continents.get(continents.size() - 1).population = rs.getInt("bevoelkerung");
            }

            // 6. Close resources
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Axis continentAxis = new CategoryAxis();
        Axis populationAxis = new NumberAxis();
        BarChart barChart = new BarChart();
        barChart.setTitle("Population by Continent");
        continentAxis.setLabel("Continent");
        populationAxis.setLabel("Population");

        XYChart.Series dataSeries = new XYChart.Series();
        for (Continent continent : continents) {
            dataSeries.getData().add(new XYChart.Data(continent.name, continent.population));
        }
        barChart.getData().add(dataSeries);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("World 2 DB Visualization");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}