package models;

public class publication {
    int id;
    String titre;
    String contenu;

    String imageurl;
    int user_id;
    private static final publication instance = new publication();
    public publication() {
    }

    public publication(String titre, String contenu, String imageurl) {
        this.titre = titre;
        this.contenu = contenu;
        this.imageurl = imageurl;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getContenu() {
        return contenu;
    }
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    public String getImage() {
        return imageurl;
    }
    public static publication getInstance(){return instance;}
    public void setImage1(String imageurl) {
        this.imageurl = imageurl;
    }
    /* private List<commentaire> comments;


    public boolean hasComments() {
        return !(comments.isEmpty());
    }*/
}
