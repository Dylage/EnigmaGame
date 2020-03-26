package fr.unilim.iut.enigmagame;

/**
*  Une énigme est caractérisée par une question et une réponse
*/
class Enigme {
    private int id;
    /**
     * Question de l'énigme
     */
    private String enigme;
    /**
     * Réponse qui y correspond
     * TODO : remplacer par une liste pour les fautes de français ?
     */
    private String reponse;
    private int score;

    public Enigme() {
    }

    @Override
    public String toString() {
        return  "ID : " + id +
                "\nenigme : " + enigme +
                "\nreponse : " + reponse ;
    }

    public Enigme(String enigme, String reponse) {
        this.enigme = enigme;
        this.reponse = reponse;
    }

    public int getId() {
        return id;
    }

    public String getEnigme() {
        return enigme;
    }

    public String getReponse() {
        return reponse;
    }

    public int getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnigme(String enigme) {
        this.enigme = enigme;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setScore(int score) {
        this.score = score;
    }

}