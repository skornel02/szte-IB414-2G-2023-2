package hu.alkfejl.dao;

import hu.alkfejl.model.Videojatek;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideojatekSQLiteImpl implements VideojatekDAO {
    enum Query {
        INSERT("INSERT INTO Videojatek (cim, fejleszto, besorolas, ar) VALUES (?,?,?,?)"),
        UPDATE("UPDATE Videojatek SET besorolas=? WHERE cim=?"),
        FILTER_MULTI("SELECT * FROM Videojatek WHERE 1 = 1"),

        DELETE("DELETE FROM Videojatek WHERE cim=?;");

        private final String queryString;
        Query(String q) { queryString = q; }
        @Override
        public String toString() {
            return queryString;
        }
    }
    private final String dbURL;

    public VideojatekSQLiteImpl(String dbPath) {
        try {
            Class.forName("org.sqlite.JDBC");
            dbURL = dbPath;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean add(Videojatek videojatek) {
        boolean result = false;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement ps = conn.prepareStatement(Query.INSERT.queryString)
        ) {
            int index = 1;
            ps.setString(index++, videojatek.getCim());
            ps.setString(index++, videojatek.getFejleszto());
            ps.setInt(index++, videojatek.getBesorolas());
            ps.setInt(index++, videojatek.getAr());

            int rows = ps.executeUpdate();
            if ( rows == 1 ) {
                result = true;
            }
        } catch (SQLException e) {
            result = false;
            System.err.println("SQLError in Videojatek add: " + e.getMessage());
        }

        return result;
    }

    @Override
    public List<Videojatek> find(Videojatek videojatek) {
        var results = new ArrayList<Videojatek>();
        StringBuilder multiQuery = new StringBuilder(Query.FILTER_MULTI.queryString);
        if ( videojatek.getCim() != null ) {
            multiQuery.append(" AND cim = ?");
        }
        if ( videojatek.getAr() != null ) {
            multiQuery.append(" AND ar = ?");
        }
        if ( videojatek.getFejleszto() != null ) {
            multiQuery.append(" AND fejleszto = ?");
        }
        if ( videojatek.getBesorolas() != null ) {
            multiQuery.append(" AND besorolas = ?");
        }

        try (Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement ps = conn.prepareStatement(multiQuery.toString())
        ) {
            int index = 1;
            if ( videojatek.getCim() != null ) {
                ps.setString(index++, videojatek.getCim());
            }
            if ( videojatek.getAr() != null ) {
                ps.setInt(index++, videojatek.getAr());
            }
            if ( videojatek.getFejleszto() != null ) {
                ps.setString(index++, videojatek.getFejleszto());
            }
            if ( videojatek.getBesorolas() != null ) {
                ps.setInt(index, videojatek.getBesorolas());
            }

            ResultSet rs = ps.executeQuery();
            while( rs.next() ) {
                results.add( retriveUtazasFromRS( rs ) );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    @Override
    public boolean update(Videojatek videojatek) {
        boolean result = false;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement ps = conn.prepareStatement(Query.UPDATE.queryString)
        ) {
            int index = 1;
            ps.setInt(index++, videojatek.getBesorolas());
            ps.setString(index++, videojatek.getCim());

            int rows = ps.executeUpdate();
            if ( rows == 1 ) {
                result = true;
            }
        } catch (SQLException e) {
            result = false;
            System.err.println("SQLError in Videojatek update: " + e.getMessage());
        }

        return result;
    }

    @Override
    public boolean delete(Videojatek videojatek) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement ps = conn.prepareStatement(Query.DELETE.queryString)
        ) {
            ps.setString(1, videojatek.getCim());

            int rows = ps.executeUpdate();
            if ( rows == 1 ) {
                return true;
            }
        } catch (SQLException e) {

            System.err.println("SQLError in Videojatek update: " + e.getMessage());
            return false;
        }

        return false;
    }

    private Videojatek retriveUtazasFromRS(ResultSet rs) throws SQLException {
        return new Videojatek()
                .setCim(rs.getString("cim"))
                .setFejleszto(rs.getString("fejleszto"))
                .setBesorolas(rs.getInt("besorolas"))
                .setAr(rs.getInt("ar"));
    }
}
