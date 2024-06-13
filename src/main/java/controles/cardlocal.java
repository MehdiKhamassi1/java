package controles;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import models.local;
import services.localService;

import java.io.IOException;
import java.io.InputStream;

public class cardlocal {
    local data = local.getInstance();

    @FXML
    private HBox hbox;

    @FXML
    private Label adresse;

    @FXML
    private ImageView imageRdv;

    @FXML
    private Label imageR;

    @FXML
    private Label id;

    @FXML
    private Label nom;

    @FXML
    private Button supprimer;

    private localService ls = new localService();
    private final MapPoint guarage = new MapPoint(36.8026669, 10.1634124);

    @FXML
    private ScrollPane mapContainer;

    @FXML
    private VBox address;

    public void initialize() {
        MapView mapView = createMapView();
        address.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
    }

    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(700, 550);
        mapView.addLayer(new CustomMapLayer());
        mapView.setZoom(15);
        mapView.flyTo(0, guarage, 0.1);
        return mapView;
    }

    private class CustomMapLayer extends MapLayer {

        private final Node marker;

        public CustomMapLayer() {
            marker = new Circle(8, Color.BLUE);
            getChildren().add(marker);

        }

        @Override
        protected void layoutLayer() {
            Point2D point = getMapPoint(guarage.getLatitude(), guarage.getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
        }
    }


    @FXML
    void del(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete ?");
        alert.setContentText("Are you sure you want to delete this ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ls.delete(Integer.parseInt(id.getText()));
                refreshView();
            }
        });
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        // Update data object
        data.setId(Integer.parseInt(id.getText()));
        data.setNom(nom.getText());
        data.setAdresse(adresse.getText());

        // Load editlocal.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/editlocal.fxml"));
        hbox.getScene().setRoot(root);
    }

    public void setData(local l) {
        nom.setText(l.getNom());
        adresse.setText(l.getAdresse());
        id.setText(String.valueOf(l.getId()));
        displayUserImage(l);
    }

    private void displayUserImage(local local) {
        String imagePath = local.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image1 = new  Image(imagePath);
            imageRdv.setImage(image1);
        } else {
            try {
                InputStream inputStream = getClass().getResourceAsStream("/default_profile_image.png");
                Image defaultImage = new Image(inputStream);
                imageRdv.setImage(defaultImage);
            } catch (NullPointerException e) {
                System.err.println("Default image not found: " + e.getMessage());
            }
        }
    }

    private void refreshView() {
        try {
            // Load the same FXML file to refresh the view
            Parent root = FXMLLoader.load(getClass().getResource("/listeloc.fxml"));
            hbox.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}