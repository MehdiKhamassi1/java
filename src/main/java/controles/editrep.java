package controles;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.reclamation;
import models.reponse;
import services.DataValidation;
import services.ReclamationService;
import services.ReponseService;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class editrep {
    reclamation data =reclamation.getInstance();
    private ReponseService rss = new ReponseService();
    private ReclamationService rs = new ReclamationService();
    @FXML
    private Button annuler;

    @FXML
    private TextField id;

    @FXML
    private Button modifier;

    @FXML
    private TextField rep;
    @FXML
    private Label txtlabel;
    @FXML
    void initialize() throws SQLException {
        reponse r =rss.affichagerep(data.getId());
        id.setText(String.valueOf(r.getId()));
        rep.setText(r.getResponse());

    }

    @FXML
    void annuler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/homea.fxml"));
        annuler.getScene().setRoot(root);
    }

    @FXML
    void mod(ActionEvent event) throws SQLException, IOException {
        boolean sujetAlphabetic = DataValidation.textAlphabet(rep,txtlabel, "Please only enter letters from a - z");
        boolean sujetEmpty=DataValidation.textFieldIsNull(rep,txtlabel,"Should not be empty");

        boolean sujetValid = !sujetEmpty && sujetAlphabetic;


        if (sujetValid ) {
            reponse r = rss.affichagerep(data.getId());
            r.setResponse(rep.getText());
            rss.update(r);
            Parent root = FXMLLoader.load(getClass().getResource("/homea.fxml"));
            annuler.getScene().setRoot(root);
        }
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