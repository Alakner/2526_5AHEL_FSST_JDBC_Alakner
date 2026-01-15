package org.example.jdbcdatenvisualisierung;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.chart.BarChart;
import java.sql.*;

public class Continent from ArrayList<Continent>{
    String name;
    int population;
}


public class JDBCController implements Initializable {
    @FXML
    private BarChart barChart;
    @FXML
    private Label statusLabel;


    void initialize() {
        // Initialisierungscode hier
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
    }

    new

}