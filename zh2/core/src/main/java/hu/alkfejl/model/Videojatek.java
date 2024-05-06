package hu.alkfejl.model;

import java.util.Objects;

/**
 * Modell osztály default konstruktorral és setter/getter fgv-ekel.
 * */
public class Videojatek {
    private String cim;
    private Integer besorolas;
    private String fejleszto;
    private Integer ar;

    /**
     * A cim a kulcs!
     * */
    public String getCim() {
        return cim;
    }

    /**
     * A cim a kulcs!
     * */
    public Videojatek setCim(String cim) {
        this.cim = cim;
        return this;
    }

    public Integer getBesorolas() {
        return besorolas;
    }

    public Videojatek setBesorolas(Integer besorolas) {
        this.besorolas = besorolas;
        return this;
    }

    public String getFejleszto() {
        return fejleszto;
    }

    public Videojatek setFejleszto(String fejleszto) {
        this.fejleszto = fejleszto;
        return this;
    }

    public Integer getAr() {
        return ar;
    }

    public Videojatek setAr(Integer ar) {
        this.ar = ar;
        return this;
    }

    @Override
    public String toString() {
        return "Zene [cim=" + cim + ", fejleszto=" + fejleszto + ", ar=" + ar + ", besorolas=" + besorolas + "]";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Videojatek videojatek) {
            return Objects.equals(ar, videojatek.ar) &&
                    Objects.equals(cim, videojatek.cim) &&
                    Objects.equals(fejleszto, videojatek.fejleszto) &&
                    Objects.equals(besorolas, videojatek.besorolas);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(cim, fejleszto, ar, besorolas);
    }
}
