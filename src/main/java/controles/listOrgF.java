package controles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Don;
import models.Organisation;
import services.OrganisationService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class listOrgF {

    @FXML
    private TableView<Organisation> table;
    private TableColumn<Organisation, Integer> idColumn;
    private TableColumn<Organisation, String> nomColumn;
    private TableColumn<Organisation, String> emailColumn;
    private TableColumn<Organisation, String> adresseColumn;
    private TableColumn<Organisation, Integer> num_telColumn;
    private TableColumn<Organisation, String> userimageColumn;

    @FXML
    private TextField adresse;
    @FXML
    private TextField image;
    @FXML
    private Label imageLabel;
    @FXML
    private ImageView ima;

    @FXML
    private Button ajoutOrg;

    @FXML
    private Button delete;

    @FXML
    private TextField email;

    @FXML
    private TextField nom;

    @FXML
    private TextField num_tel;

    @FXML
    private Label emailLabel;
    @FXML
    private Label adresseLabel;
    @FXML
    private Label nomLabel;

    @FXML
    private Label num_telLabel;

    @FXML
    private TextField search;

    @FXML
    private Button update;

    @FXML
    private GridPane gridpane;
    private OrganisationService os = new OrganisationService();

    @FXML
    void initialize() throws SQLException, IOException {
        List<Organisation> organisationList = os.read();
        if (organisationList != null) {
            ObservableList<Organisation> list = FXCollections.observableList(organisationList);


            int column = 0;
            int row = 1;

            for (Organisation o : os.read()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cardOrgF.fxml"));
                HBox cardBox = fxmlLoader.load();

                CardOrg cardController = fxmlLoader.getController();
                cardController.setData(o);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                gridpane.add(cardBox, column++, row);
                GridPane.setMargin(cardBox,new javafx.geometry.Insets(10, 10, 10, 10));

        }
    }}


    @FXML
    void search(KeyEvent event) throws SQLException, IOException {
        List<Organisation> orgs = os.rech(search.getText());
        gridpane.getChildren().clear();
        int column = 0;
        int row = 1;
        for (Organisation o : orgs) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cardOrgF.fxml"));
            HBox cardBox = fxmlLoader.load();

            CardOrg cardController = fxmlLoader.getController();
            cardController.setData(o);
            if (column == 3) {
                column = 0;
                row++;
            }
            gridpane.add(cardBox, column++, row);
            GridPane.setMargin(cardBox,new javafx.geometry.Insets(10, 10, 10, 10));
        }

    }

    @FXML
    void gorec(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/testtt.fxml"));
        search.getScene().setRoot(root);
    }

    @FXML
    void goToDon(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listDon.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }


    @FXML
    void godos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXMLAjouterDossier.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void profile(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/profile.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }


    @FXML
    void gordv(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/liste.fxml"));
        search.getScene().setRoot(root);
    }


    @FXML
    void goHome(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/home2.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    public void foruum(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/addpublication.fxml"));
        search.getScene().setRoot(root);
    }

    @FXML
    void goToOrg(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listOrg.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }
}
