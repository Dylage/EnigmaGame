package fr.unilim.iut.enigmagame;

class Enigme {
    private int id;
    private String enigme;
    private String reponse;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setEnigme(String enigme) {
        this.enigme = enigme;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}