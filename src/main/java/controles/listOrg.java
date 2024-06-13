package controles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import services.DataValidation;
import services.OrganisationService;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class listOrg {

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
                fxmlLoader.setLocation(getClass().getResource("/cardOrg.fxml"));
                HBox cardBox = fxmlLoader.load();

                CardOrg cardController = fxmlLoader.getController();
                cardController.setData(o);
                if (column == 4) {
                    column = 0;
                    row++;
                }
                gridpane.add(cardBox, column++, row);
                GridPane.setMargin(cardBox,new javafx.geometry.Insets(10, 10, 10, 10));

        }
    }}



    @FXML
    void ajoutOrg(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ajouterOrg.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void home(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Home2.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void don(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listDon.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void orgF(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listOrgF.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void donB(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listDonB.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void front(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Home2.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }




    @FXML
    void update(ActionEvent event) throws SQLException {

            Organisation selection = table.getSelectionModel().getSelectedItem();

            if (selection != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirm Update");
                alert.setContentText("Are you sure you want to update this Organisation?");


                ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);


                if (result == ButtonType.OK) {
                    selection.setNom(nom.getText());
                    selection.setEmail(email.getText());
                    selection.setAdresse(adresse.getText());
                    selection.setNum_tel(num_tel.getText());

                    os.update(selection);
                    ObservableList<Organisation> list = FXCollections.observableList(os.read());
                    table.setItems(list);
                    nom.setText("");
                    adresse.setText("");
                    email.setText("");
                    num_tel.setText("");

            }
        }
    }

    @FXML
    void toDelete(ActionEvent actionEvent) throws SQLException {
        Organisation selection = table.getSelectionModel().getSelectedItem();
        if (selection != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure you want to delete the selected organisation?");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);


            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    try {
                        os.delete(selection.getId());
                        ObservableList<Organisation> list = FXCollections.observableList(os.read());
                        table.setItems(list);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select an organisation to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void search(KeyEvent event) throws SQLException, IOException {
        List<Organisation> orgs = os.rech(search.getText());
        gridpane.getChildren().clear();
        int column = 0;
        int row = 1;
        for (Organisation o : orgs) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cardOrg.fxml"));
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
    void statistiques(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/statistics.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void gotoconsultation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/testtt.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void gotodon(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/listDonB.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void gotodossier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/testtt.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);

    }

    @FXML
    void gotolocal(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Listeloc.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void gotorec(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/homea.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void gotoorg(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/listOrg.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/back.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    public void login(ActionEvent actionEvent) {
    }

    public void affuser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listuser.fxml")));
        search.getScene().setRoot(root);
    }

    public void aduser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adduser.fxml")));
        search.getScene().setRoot(root);
    }

}
