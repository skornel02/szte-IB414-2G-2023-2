package hu.alkfejl.controller;

import hu.alkfejl.dao.VideojatekDAO;
import hu.alkfejl.dao.VideojatekSQLiteImpl;
import hu.alkfejl.model.Videojatek;
import hu.alkfejl.utils.ConfigManager;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.out;

public class VideojatekController {

    private VideojatekDAO dao;
    // fontos, hogy interface alapjan egy valtozot hasznaljunk DAO elereshez
    // igy konnyen tudjuk valtoztatni, illetve
    // dinamikusan tudunk betolteni dao megvalositast
    private static final Map<String, VideojatekController> instances = new ConcurrentHashMap<>();

    private VideojatekController(String daoToUse, String dbPath) {
        switch(daoToUse) {
            case "sqlite":
                var extendedPath = dbPath.startsWith("jdbc:sqlite:") ? dbPath : "jdbc:sqlite:" + dbPath;
                dao = new VideojatekSQLiteImpl(extendedPath); break;
            default:
                try {
                    out.println("Using custom dao: " + daoToUse);
                    // barhogy megadhatjuk a peldanyositast, de fontos, hogy onnantol azt kell kovesse az egyedi megvalositas is
                    // igy egy konstuktoron keresztul barmilyen metodus hivhato, erre figyeljunk oda!
                    // ez csak egy pelda, hogy lassuk a dao valtozo interface tipusanak fontossagat!
                    Class<?> c = Class.forName(daoToUse);
                    dao = (VideojatekDAO) c.getDeclaredConstructor(String.class).newInstance(dbPath);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                         InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    /**
     *
     * Egy példányt ad vissza a dao és dbPath adatok felhasználásával.
     * @param clazz A kontrollert FELHASZNÁLÓ osztály osztálya.
     *              Ez alapjéán kiolvassa a config fájlt és a dao és dbPath értékeket beállítja, ahhoz ad egy kontrollert.
     *              A daonak az 'sqlite' és 'egyéb' a lehetséges értékeke. SQLite esetében aráfűzi a jdbc:sqlite: kezdést ha nem lenne.
     *              Egyéb megadásakor a classpath on egy osztály fully qualified nevét kell megadni. Ekkor a dbPath érintetlenül kerül átadásra.
     * */
    public static VideojatekController getInstance(Class<?> clazz){
        ConfigManager cm = new ConfigManager(clazz);
        var daoToUse = cm.getValue("dao");
        var dbPath = cm.getValue("dbPath");

        VideojatekController instance;

        if ( instances.containsKey(daoToUse + dbPath) ) {
            instance = instances.get(daoToUse + dbPath);
        } else {
            instance = new VideojatekController(daoToUse, dbPath);
            instances.put(daoToUse + dbPath, instance);
        }

        return instance;
    }

    /**
     * Egy új videojateket ad át a kiválasztott dao-nak add-ra. Annak értékét adja vissza.
     * Ha Harvey Weinstein a rendező, minden esetben hamisat ad vissza, nem csinál semmit.
     * */
    public boolean add(Videojatek videojatek) {
        if ( "Harvey Weinstein".equals( videojatek.getBesorolas() ) ) {
            return false;
        }
        return dao.add(videojatek);
    }

    /**
     * Frissíti egy adott című videojatek besorolás értékét. Hamisat ad vissza, ha nem sikerült a frissítés, egyébként igazat.
     * */
    public boolean update(Videojatek videojatek){
        return dao.update(videojatek);
    }

    /**
     * Visszaadja a kiválasztott dao által adott listát a keresési filter alapján.
     * A filternek be kell állítani mindent amire szűrni szeretnénk, egy sablon objektumota dunk át.
     * */
    public List<Videojatek> find(Videojatek filter) {
        return dao.find(filter);
    }

    public boolean delete(Videojatek videojatek){return dao.delete(videojatek);}

    /**
     * Clear all dao instances. Not intended for use!
     * */
    public static void clearInstances() {
        instances.clear();
    }
}
