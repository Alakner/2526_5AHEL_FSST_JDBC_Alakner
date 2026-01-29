package org.example.jdbcdatenvisualisierung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

// TODO Wittner: Continent Klasse durch Methdoden ergänzen, um Datenkapselung zu verbessern
class Continent {
    String name;
    double population;
}

// TODO Wittner: Ermitteln Sie welche Kontinente in der Datenbank enthalten sind.
//    erstellen Sie für jeden Kontinent eine Chceckbox in der GUI, mit der der Kontinent ein- und ausgeblendet werden kann.
//    Stellen Sie im BarChart nur die Kontinente dar, die ausgewählt sind.

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
                c.population = rs.getDouble("bevoelkerung");
                continents.add(c);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (Continent continent : continents) {
            dataSeries.getData().add(new XYChart.Data<>(continent.name, continent.population));
        }

        FXMLLoader fxmlLoader = new FXMLLoader(JDBCApplication.class.getResource("jdbcview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);

        // GUI mit Daten füllen
        JDBCController controller = fxmlLoader.getController();
        controller.setDataSeries(dataSeries);

        stage.setTitle("World 2 DB Visualization");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
