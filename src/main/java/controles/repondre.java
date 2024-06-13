package controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.reclamation;
import models.reponse;
import models.notif;
import services.DataValidation;
import services.ReclamationService;
import services.ReponseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javafx.scene.control.Label;
import services.notifservice;

public class repondre {
    private ReclamationService rs = new ReclamationService();
    private ReponseService rss = new ReponseService();
    reclamation data =reclamation.getInstance();
    private notifservice ns = new notifservice();
    @FXML
    private Label txtlabel;
    @FXML
    private Button rep;

    @FXML
    private TextField txt;
    @FXML
    private Button anuuler;

    @FXML
    void rep(ActionEvent event) throws SQLException, IOException {
        boolean sujetAlphabetic = DataValidation.textAlphabet(txt,txtlabel, "Please only enter letters from a - z");
        boolean sujetEmpty=DataValidation.textFieldIsNull(txt,txtlabel,"Should not be empty");

        boolean sujetValid = !sujetEmpty && sujetAlphabetic;


        if (sujetValid ) {
            reponse r = new reponse();
            r.setResponse(txt.getText());
            r.setReclamation_id(data.getId());

            rss.create(r);
            Parent root = FXMLLoader.load(getClass().getResource("/homea.fxml"));
            txt.getScene().setRoot(root);
        }
        notif n=new notif(data.getId());
        ns.create(n);
    }
    @FXML
    void ff(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/homea.fxml"));
        txt.getScene().setRoot(root);
    }





    /*public void affuser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/listuser.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    public void aduser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/adduser.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }*/

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


}
