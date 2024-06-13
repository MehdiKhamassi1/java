package controles;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.SessionManager;
import models.user;
import services.userservice;
import toolkit.MyTools;
import toolkit.QRCodeGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class profile {

    @FXML
    private Label email;
    userservice us= new userservice();

    @FXML
    private Label nom;

    @FXML
    private Label prenom;


    private user currentUser;
    @FXML
    private ImageView userImageView;


    @FXML
    private ImageView qrCodeImageView;


    public void initialize() {
        currentUser = SessionManager.getCurrentUser();
        System.out.println("IDdddddddddd: ");

        if (currentUser != null) {
            System.out.println("ccccccccccccc: ");

            displayUserInfo(currentUser);
            System.out.println("userinffff");
            System.out.println(currentUser.getId());
            System.out.println(currentUser.getEmail());
            System.out.println(currentUser.getProfileImage());
            displayUserImage(currentUser);
            displayuserqrcode(currentUser);
            System.out.println("ID: " + currentUser.getId());
            System.out.println("Email: " + currentUser.getEmail());

        } else {
            System.out.println("Utilisateur actuel non défini.");
        }
    }

    private void displayUserInfo(user user) {
//idLabel.setText(String.valueOf(user.getId()));
        email.setText(user.getEmail());
        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        //numtelField.setText(String.valueOf(user.getNumtel()));
       // genderField.setText(user.getGender());



    }
/*
    private void displayUserImage(user user) {
        String imagePath = user.getProfileImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(imagePath);
            userImageView.setImage(image);
        } else {
            Image defaultImage = new Image(getClass().getResourceAsStream("default_profile_image.jpg"));
            userImageView.setImage(defaultImage);
        }
    }
*/
@FXML
private void displayUserImage(user user) {


    String imagePath = user.getProfileImage();
    if (imagePath == null) {
        System.out.println("Le chemin de l'image est null.");
        return;
    }
    String fileName = new File(imagePath).getName(); // Obtenir seulement le nom du fichier avec son extension

    // Afficher le nom du fichier dans le champ de texte
   // profileImage.setText(fileName);

    // Construire le chemin d'accès complet à l'image
    imagePath = "file:/C:/xampp/htdocs/webjava/pidevF/public/uploads/" + imagePath;
    System.out.println("image pathhhhhhhhh:");
    System.out.println(imagePath);

    if (imagePath != null && !imagePath.isEmpty()) {
        Image image = new Image(imagePath);
        userImageView.setImage(image);
    } else {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/default_profile_image.jpg");
            Image defaultImage = new Image(inputStream);
            userImageView.setImage(defaultImage);
        } catch (NullPointerException e) {
            System.err.println("Image par défaut introuvable : " + e.getMessage());
        }
    }
}



    private void displayuserqrcode(user user )
    {
        String userData =
                "nom et prenom : " +user.getNom() + " " + user.getPrenom() + "\n" +
                        "Gender: " + user.getGender() + "\n" +
                        "Email: " + user.getEmail()+ "\n"+
                        "Roles"+ user.getRoles()
                ;
        BufferedImage qrCodeImage = QRCodeGenerator.generateQRCode(userData, 200, 200);

        if (qrCodeImage != null) {
            Image fxImage = SwingFXUtils.toFXImage(qrCodeImage, null);
            qrCodeImageView.setImage(fxImage);
        }




    }
    public void handleDownloadButton(javafx.event.ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer l'image");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PNG", "*.png"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                javafx.scene.image.Image image = qrCodeImageView.getImage();
                java.awt.image.BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }

    }


    public void update(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/updateprofile.fxml"));
        nom.getScene().setRoot(root);
    }

    public void back(ActionEvent actionEvent) throws IOException {
        currentUser = SessionManager.getCurrentUser();

        if (currentUser.getRoles().contains("ROLE_ADMIN"))
        {

            Parent root = FXMLLoader.load(getClass().getResource("/back.fxml"));
            prenom.getScene().setRoot(root);
        }
        else
        {
        Parent root = FXMLLoader.load(getClass().getResource("/home2.fxml"));
        prenom.getScene().setRoot(root);
        }
    }

    public void updatepassword(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/updatepassword.fxml"));
        prenom.getScene().setRoot(root);
    }

    @FXML
    public void del(javafx.event.ActionEvent actionEvent) throws SQLException {
        currentUser = SessionManager.getCurrentUser();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Confirmation de la suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer votre compte ?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    us.delete(currentUser.getId());
                    MyTools.showAlertInfo("Suppression réussie", "Votre compte a été supprimé avec succès !");
                    MyTools.goTo("/login.fxml", prenom);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
