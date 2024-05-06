package hu.alkfejl.dao;

import hu.alkfejl.model.Videojatek;

import java.util.List;

public interface VideojatekDAO {

    /**
     * Hozzáad az adatbázishoz.
     * @param videojatek a kapott videojatek objektum amit beszúr az adatbázisba
     *             A videojateknek a címe egyedi, így ha olyat adunk meg ami már létezik, akkor hibát kapunk.
     * */
    boolean add(Videojatek videojatek);
    /**
     * Listázza a videojatekeket.
     * @param filter filter objektum aminek a mintájára mindent kilistáz ami illeszkedik a filterre.
     *               Amire szűrni akarunk, azt állítsuk be a filter objektumnak.
     *               A videojateknek a címe egyedi, így ha azt beállítjuk csak egy videojateket kapunk
     * */
    List<Videojatek> find(Videojatek filter);

    boolean update(Videojatek videojatek);

    boolean delete(Videojatek videojatek);
}
