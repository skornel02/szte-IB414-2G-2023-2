package hu.skornel02.entities;

public class BunozoEntity {
    private int id;

    private String nev;
    private String banda;

    private int korozes;

    public BunozoEntity() {
        nev = null;
        banda = null;
        korozes = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public int getKorozes() {
        return korozes;
    }

    public void setKorozes(int korozes) {
        this.korozes = korozes;
    }
}
