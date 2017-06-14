package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain. Keskustelualue;

public class  KeskustelualueDao implements Dao< Keskustelualue, Integer> {

    private Database database;

    public   KeskustelualueDao(Database database) {
        this.database = database;
    }

    @Override
    public  Keskustelualue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM  Keskustelualue a WHERE a.id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

         Keskustelualue keskustelualuex = new  Keskustelualue(id,nimi);

        rs.close();
        stmt.close();
        connection.close();

        return keskustelualuex;
    }

    @Override
    public List< Keskustelualue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
        "SELECT nimi, otsikko FROM Keskustelualue a "
                + "LEFT JOIN Keskustelu k ON (a.id = k.alue) WHERE k.id = 1;"
                );

        ResultSet rs = stmt.executeQuery();
        List< Keskustelualue> keskustelualueet = new ArrayList<>();
        while (rs.next()) {
   
        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

            keskustelualueet.add(new  Keskustelualue(id,nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelualueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}

