package com.btsinfo.topseriz;

public class Series {

    int id;
    String titre;
    String affiche_url;
    String annee_production;
    String synopsis;
    String genre;
    String Artistes;

    public Series(int id, String titre, String affiche_url, String annee_production) {
        this.id = id;
        this.titre = titre;
        this.affiche_url = affiche_url;
        this.annee_production = annee_production;
    }

    public Series(int id, String titre, String affiche_url, String annee_production, String synopsis, String genre, String artistes) {
        this.id = id;
        this.titre = titre;
        this.affiche_url = affiche_url;
        this.annee_production = annee_production;
        this.synopsis = synopsis;
        this.genre = genre;
        Artistes = artistes;
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

    public String getAffiche_url() {
        return affiche_url;
    }

    public void setAffiche_url(String affiche_url) {
        this.affiche_url = affiche_url;
    }

    public String getAnnee_production() {
        return annee_production;
    }

    public void setAnnee_production(String annee_production) {
        this.annee_production = annee_production;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtistes() {
        return Artistes;
    }

    public void setArtistes(String artistes) {
        Artistes = artistes;
    }
}
