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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Don;
import models.user;
import services.DonService;
import services.userservice;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.List;
import java.util.Objects;

public class listDonB {
    private user currentUser;
    userservice us = new userservice();

    @FXML
    private Text ajout;

    @FXML
    private Text nomlabel;
    @FXML
    private Label nbdocteur;

    @FXML
    private Label nbpatient;
    @FXML
    private Button pro;
    private EventObject event;

    @FXML
    private ImageView profileimage;


    @FXML
    private TableView<Don> table;
    private TableColumn<Don, String> organisationnomColumn;
    private TableColumn<Don, Integer> organisationColumn;
    private TableColumn<Don, Integer> donidColumn;
    private TableColumn<Don, String> donnomColumn;
    private TableColumn<Don, String> donprenomColumn;
    private TableColumn<Don, String> donemailColumn;
    private TableColumn<Don, String> dondescriptionColumn;
    private TableColumn<Don, String> dontypeColumn;
    private TableColumn<Don, String> donimageColumn;

    private TableColumn<Don, Integer> donmontantColumn;

    @FXML
    private TextField image;

    @FXML
    private Label imageLabel;

    @FXML
    private GridPane gridpane;

    @FXML
    private Button delete;
    @FXML
    private ImageView ima;
    @FXML
    private TextField description;
    @FXML
    private Label prenomLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label montantLabel;
    @FXML
    private TextField email;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField montant;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField search;

    private DonService ds = new DonService();

    @FXML
    void initialize() throws SQLException, IOException {
        ObservableList<Don> list = FXCollections.observableList(ds.reada());

        ObservableList<String> donationTypes = FXCollections.observableArrayList(
                "Dons monétaires",
                "Dons d'équipements"
        );



        int column = 0;
        int row = 1;
        for (Don d : ds.read()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cardDon.fxml"));
            HBox cardBox = fxmlLoader.load();

            CardDon cardController = fxmlLoader.getController();
            cardController.setData(d);
            if (column == 2) {
                column = 0;
                row++;
            }
            gridpane.add(cardBox, column++, row);
            GridPane.setMargin(cardBox, new javafx.geometry.Insets(10, 10, 10, 10));
        }
    }
    @FXML
    void toadd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ajouterDon.fxml")));
        table.getScene().setRoot(root);
    }

    @FXML
    void search(KeyEvent event) throws SQLException, IOException {
        List<Don> Dons = ds.rech(search.getText());
        gridpane.getChildren().clear();
        int column = 0;
        int row = 1;
        for (Don d : Dons) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cardDonF.fxml"));
            HBox cardBox = fxmlLoader.load();

            CardDonF CardDonF = fxmlLoader.getController();
            CardDonF.setData(d);
            if (column == 3) {
                column = 0;
                row++;
            }
            gridpane.add(cardBox, column++, row);
            GridPane.setMargin(cardBox,new Insets(10, 10, 10, 10));
        }

    }

    @FXML
    void select(MouseEvent event) {
        Don selection = table.getSelectionModel().getSelectedItem();
        if (selection != null) {
            nom.setText(selection.getNom());
            prenom.setText(selection.getPrenom());
            email.setText(selection.getEmail());
            typeComboBox.setValue(selection.getType());
            description.setText(selection.getDescription());
            image.setText(selection.getImage());
            montant.setText(String.valueOf(selection.getMontant()));

            displayUserImage(selection);


        }
    }
    private void displayUserImage(Don don) {
        String imagePath = don.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(imagePath);
            ima.setImage(image);
        } else {

            Image defaultImage = new Image(getClass().getResourceAsStream("default_profile_image.png"));
            ima.setImage(defaultImage);
        }
    }
    @FXML
    void update(ActionEvent event) throws SQLException {

        Don selection = table.getSelectionModel().getSelectedItem();
        if (selection != null) {
            if (nom.getText().isEmpty() || prenom.getText().isEmpty() || email.getText().isEmpty() ||
                    typeComboBox.getValue() == null || description.getText().isEmpty() || montant.getText().isEmpty()) {
                Alert emptyFieldsAlert = new Alert(Alert.AlertType.ERROR);
                emptyFieldsAlert.setTitle("Error");
                emptyFieldsAlert.setHeaderText(null);
                emptyFieldsAlert.setContentText("Please fill in all fields.");
                emptyFieldsAlert.showAndWait();
                return;
            }


            try {
                Integer.parseInt(montant.getText());
            } catch (NumberFormatException e) {
                Alert invalidAmountAlert = new Alert(Alert.AlertType.ERROR);
                invalidAmountAlert.setTitle("Error");
                invalidAmountAlert.setHeaderText(null);
                invalidAmountAlert.setContentText("Please enter a valid numeric amount.");
                invalidAmountAlert.showAndWait();
                return;
            }


            String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
            if (!email.getText().matches(emailPattern)) {
                Alert invalidEmailAlert = new Alert(Alert.AlertType.ERROR);
                invalidEmailAlert.setTitle("Error");
                invalidEmailAlert.setHeaderText(null);
                invalidEmailAlert.setContentText("Please enter a valid email address.");
                invalidEmailAlert.showAndWait();
                return;
            }


            String namePattern = "[a-zA-Z]+";
            if (!nom.getText().matches(namePattern) || !prenom.getText().matches(namePattern)) {
                Alert invalidNameAlert = new Alert(Alert.AlertType.ERROR);
                invalidNameAlert.setTitle("Error");
                invalidNameAlert.setHeaderText(null);
                invalidNameAlert.setContentText("Please enter valid alphabetic characters for name and surname.");
                invalidNameAlert.showAndWait();
                return;
            }


            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Confirm Update");
            confirmationAlert.setContentText("Are you sure you want to update this entry?");


            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);


            if (result == ButtonType.OK) {
                selection.setNom(nom.getText());
                selection.setPrenom(prenom.getText());
                selection.setEmail(email.getText());
                selection.setType(typeComboBox.getValue());
                selection.setDescription(description.getText());
                selection.setMontant(Integer.parseInt(montant.getText()));
                ds.update(selection);
                ObservableList<Don> list = FXCollections.observableList(ds.read());
                table.setItems(list);
                clearFields();
            }
        }
    }


    @FXML
    void toDelete(ActionEvent actionEvent) throws SQLException {
        Don selection = table.getSelectionModel().getSelectedItem();
        if (selection != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Don");
            alert.setContentText("Are you sure you want to delete this Don?");


            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {

                        ds.delete(selection.getId());
                        ObservableList<Don> list = FXCollections.observableList(ds.read());
                        table.setItems(list);
                    } catch (SQLException e) {
                        e.printStackTrace();

                    }
                }
            });
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Don Selected");
            alert.setContentText("Please select a Don to delete.");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        nom.clear();
        prenom.clear();
        email.clear();
        typeComboBox.getSelectionModel().clearSelection();
        description.clear();
        montant.clear();
    }

    public void selectImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();


        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);


        Stage stage = (Stage) table.getScene().getWindow(); // Get the current stage
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            imageLabel.setText(file.getName());

        }
    }

    @FXML
    void goToHome(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Home2.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }
    @FXML
    void goToDon(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listDon.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void goToDon2(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listDon.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void orgB(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listOrg.fxml")));
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

