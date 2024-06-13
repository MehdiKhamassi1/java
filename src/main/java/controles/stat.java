package controles;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.*;

public class stat {
    @FXML
    private PieChart pieChart;

    @FXML
    private Button bt_cancel;
    private final String USER = "root";
    private final String PASS = "";
    String url = "jdbc:mysql://localhost:3306/not";

    public void initialize() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(url, USER, PASS)) {
            String sql = "SELECT p.titre, COUNT(c.id) AS NombreCommentaires "
                    + "FROM Publication p "
                    + "LEFT JOIN Commentaire c ON c.publication_id = p.id "
                    + "GROUP BY p.titre";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String titre = rs.getString("titre");
                int nombreCommentaires = rs.getInt("NombreCommentaires");
                pieChartData.add(new PieChart.Data(titre, nombreCommentaires));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Platform.runLater(() -> {
            pieChart.setData(pieChartData);
        });
    }
    @FXML
    void cancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/addpublication.fxml"));
            bt_cancel.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
