package controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import models.SessionManager;
import models.reclamation;
import models.user;
import org.controlsfx.control.Notifications;
import services.DataValidation;
import services.ReclamationService;
import javafx.scene.control.Label;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import services.MyEmailSender;
import java.net.URI;
import java.util.Objects;

public class add {
    private user sessionManager= SessionManager.getCurrentUser();
    private final ReclamationService rs=new ReclamationService();

    @FXML
    private Button ajouter;

    @FXML
    private Button annuler;

    @FXML
    private TextField contenu;

    @FXML
    private Label contenuLabel;

    @FXML
    private TextField sujet;

    @FXML
    private Label sujetLabel;

    @FXML
    private TextField userid;


    @FXML
    void initialize() throws SQLException {
        userid.setText(String.valueOf(sessionManager.getId()));


    }

    @FXML
    void addReclamation(ActionEvent event) throws SQLException {

        boolean sujetAlphabetic = DataValidation.textAlphabetspace(sujet, sujetLabel, "Please only enter letters from a - z");
        boolean sujetEmpty=DataValidation.textFieldIsNull(sujet,sujetLabel,"Should not be empty");
        boolean contenuEmpty=DataValidation.textFieldIsNull(contenu,contenuLabel,"Should not be empty");
        boolean sujetValid = !sujetEmpty && sujetAlphabetic;
        boolean contenuValid = !contenuEmpty;

        if (sujetValid && contenuValid) {
                try {
                    rs.create(new reclamation(sujet.getText(), contenu.getText(), Integer.parseInt(userid.getText())));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Product added successfully!");
                    alert.showAndWait();

                    contenu.setText("");
                    sujet.setText("");
                    userid.setText("");
                    String mail= "Vous avez recu une nouvelle reclamation";
                    MyEmailSender.send("malekeljendoubi@gmail.com","Reset your password ",mail);
                    //sendmail();
                    Notifications notifications = Notifications.create();
                    notifications.text("reclamation ajoutée ");
                    notifications.title("Tache succes");
                    notifications.show();
                    Parent root = FXMLLoader.load(getClass().getResource("/testtt.fxml"));
                    contenu.getScene().setRoot(root);


                } catch (NumberFormatException e) {
                    showErrorAlert("Invalid user id! Please enter a valid integer.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

    }
    @FXML
    void fromAddToList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/testtt.fxml"));
            contenu.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void sendmail(){
        try {
            URI uri = new URI("http://127.0.0.1:8000/reclamation/jav");
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(uri);
                } else {
                    System.out.println("Desktop browsing is not supported on this platform.");
                }
            } else {
                System.out.println("Desktop is not supported on this platform.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


}
    @FXML
    void gorec(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/testtt.fxml"));
        annuler.getScene().setRoot(root);
    }

    @FXML
    void goToDon(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listDon.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void goToOrg(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listOrgF.fxml")));
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
    void backM(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/listOrg.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }
    @FXML
    void gordv(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/liste.fxml"));
        annuler.getScene().setRoot(root);
    }


    @FXML
    void goHome(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/home2.fxml")));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    public void foruum(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/addpublication.fxml"));
        annuler.getScene().setRoot(root);
    }

    public void goToPub(ActionEvent actionEvent) {
    }
}
