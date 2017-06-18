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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tikape.runko.domain.Keskustelu;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database database;

    public KeskusteluDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelu add(Keskustelu k) throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Keskustelu (aloittaja, alue, otsikko) VALUES (?, ?, ?)");
        stmt.setInt(2, k.getAloittaja());
        stmt.setInt(3, k.getAlue());
        stmt.setString(4, k.getOtsikko());
        
        stmt.executeUpdate();
        
        System.out.println("LISÄTÄÄN KESKUSTELU");
        
        int i = getNewid(connection);
        
        k.setId(i);
        
        stmt.close();
        connection.close();
        return k; 
    }

    public int getNewid(Connection conn) throws SQLException {
         PreparedStatement stmt = conn.prepareStatement(
        "SELECT last_insert_rowid()");       

 
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return 0;
        }    
        int id = rs.getInt("id");
        return id;
    } 
    
    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        //PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        PreparedStatement stmt = connection.prepareStatement("SELECT k.id AS id, k.otsikko, k.aloittaja, k.alue AS alue, COUNT(*) AS viestimaara, MIN(v.lahetysaika) AS avausaika, "
                + "MAX(v.lahetysaika) AS viimeisin "
                + "FROM Keskustelu k "
                + "LEFT JOIN Viesti v ON (k.id = v.keskustelu) "
                + "WHERE k.id = ?;");
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
        Integer viestimaara = rs.getInt("viestimaara");
        Timestamp avausaika = rs.getTimestamp("avausaika");

        Keskustelu keskustelux = new Keskustelu(id,aloittaja,alue,otsikko);

        rs.close();
        stmt.close();
        connection.close();

        return keskustelux;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        // Tässä on Tietokannat kovakoodattuna testausta varten
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
        "SELECT * FROM Keskustelu");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
   
            Integer id = rs.getInt("id");
            Integer aloittaja = rs.getInt("aloittaja");
            Integer alue = rs.getInt("alue");
            String otsikko = rs.getString("otsikko");

            Integer viestimaara = rs.getInt("viestimaara");
            Timestamp avausaika = rs.getTimestamp("avausaika");

        keskustelut.add(new Keskustelu(id,aloittaja,alue,otsikko));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }
    
    public List<Keskustelu> findPerAlue(String alue) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
        "SELECT k.id AS id, otsikko, aloittaja, k.alue AS alue, COUNT(*) AS viestimaara, MIN(v.lahetysaika) AS avausaika, "
                + "MAX(v.lahetysaika) AS viimeisin "
                + "FROM Keskustelualue a "
                + "LEFT JOIN Keskustelu k ON (a.id = k.alue) "
                + "LEFT JOIN Viesti v ON (k.id = v.keskustelu) "
                + "WHERE a.nimi = ? "
                + "GROUP BY otsikko "
                + "ORDER BY avausaika DESC LIMIT 10;"
                );
        stmt.setString(1, alue);

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
   
            Integer id = rs.getInt("id");
            Integer aloittaja = rs.getInt("aloittaja");
            Integer alueID = rs.getInt("alue");
            String otsikko = rs.getString("otsikko");
            int viestimaara = rs.getInt("viestimaara");
            Timestamp avausaika = rs.getTimestamp("avausaika");

            keskustelut.add(new Keskustelu(id,aloittaja,alueID,otsikko));
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
