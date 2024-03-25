package hu.skornel02.repositories;

import hu.skornel02.ConfigurationManager;
import hu.skornel02.entities.BunozoEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BunozoRepository {
    private static BunozoRepository instance;

    private static final String Select = "select Id, Nev, Banda, Korozes from Bunozo;";
    private static final String Insert = "INSERT INTO Bunozo (Nev, Banda, Korozes) VALUES (?, ?, ?);";

    private final String connectionUrl;

    private  BunozoRepository(String conn) {
        connectionUrl = conn;
        try{
            Class.forName("org.sqlite.JDBC");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static BunozoRepository getInstance(){
        if(instance == null){
            synchronized (BunozoRepository.class){
                if(instance == null){
                    instance = new BunozoRepository(ConfigurationManager.getConnectionUrl());
                }
            }
        }
        return instance;
    }

    public boolean add(BunozoEntity bunozo) {

        try(var c = DriverManager.getConnection(connectionUrl);
            var pst = c.prepareStatement(Insert, Statement.RETURN_GENERATED_KEYS))
        {
            pst.setString(1,bunozo.getNev());
            pst.setString(2, bunozo.getBanda());
            pst.setInt(3, bunozo.getKorozes());

            int affectedRows = pst.executeUpdate();

            return affectedRows == 1;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public List<BunozoEntity> find(BunozoEntity filter) {
        var result = new ArrayList<BunozoEntity>();

        try(var c = DriverManager.getConnection(connectionUrl);
            var stmt = c.createStatement();
            var rs = stmt.executeQuery(Select))
        {
            while(rs.next()){
                var bunozo = new BunozoEntity();
                bunozo.setId(rs.getInt("Id"));
                bunozo.setNev(rs.getString("Nev"));
                bunozo.setBanda(rs.getString("Banda"));
                bunozo.setKorozes(rs.getInt("Korozes"));

                result.add(bunozo);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        var resultStream = result.stream();

        if (filter != null) {
            resultStream = resultStream
                    .filter(bunozo -> filter.getNev() == null || bunozo.getNev().contains(filter.getNev()))
                    .filter(bunozo -> filter.getBanda() == null || bunozo.getBanda().equals(filter.getBanda()))
                    .filter(bunoyo -> filter.getKorozes() == -1 || bunoyo.getKorozes() == filter.getKorozes());
        }

        return resultStream.collect(Collectors.toList());
    }

}
