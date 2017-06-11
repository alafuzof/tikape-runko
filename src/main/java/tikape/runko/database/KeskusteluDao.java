/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelu;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database database;

    public KeskusteluDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = 3");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer aloittaja = rs.getInt("aloittaja");
        Integer alue = rs.getInt("alue");
        String otsikko = rs.getString("otsikko");

        Keskustelu k = new Keskustelu(id,aloittaja,alue,otsikko);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
        "SELECT otsikko, COUNT(*) AS maara, MIN(v.lahetysaika) AS avausaika, "
                + "MAX(v.lahetysaika) AS viimeisin "
                + "FROM Keskustelualue a "
                + "LEFT JOIN Keskustelu k ON (a.id = k.alue) "
                + "LEFT JOIN Viesti v ON (k.id = v.keskustelu) "
                + "WHERE a.nimi = 'Ponit' "
                + "GROUP BY otsikko "
                + "ORDER BY avausaika DESC LIMIT 10"
                );

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
   
        Integer id = rs.getInt("id");
        Integer aloittaja = rs.getInt("aloittaja");
        Integer alue = rs.getInt("alue");
        String otsikko = rs.getString("otsikko");

            keskustelut.add(new Keskustelu(id,aloittaja,alue,otsikko));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
