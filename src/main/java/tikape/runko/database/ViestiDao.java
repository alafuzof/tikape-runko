/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

/**
 *
 * @author tikape
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;

public class ViestiDao implements Dao<Viesti, Integer> {
    private Database database;
    
    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti add(Viesti v) throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (?, ?, ?, ?)");
        stmt.setInt(1, v.getKirjoittajaID());
        stmt.setInt(2, v.getKeskustelu());
        stmt.setTimestamp(3, v.getLahetysaika());
        stmt.setString(4, v.getSisalto());
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
        return v; // Note may contain invalid id!
    }
   
    
    @Override
    public Viesti findOne(Integer key) throws SQLException {
        return new Viesti();
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        ArrayList<Viesti> viestit = new ArrayList();
        return viestit;
    }
    
    public List<Viesti> findAllByKeskustelu(int keskustelu) throws SQLException {
        //return viestit;
        System.out.println("Haetaan viestejä keskustelusta: " + keskustelu);
        
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
        "SELECT k.id as kirjoittaja_id, k.tunnus AS kirjoittaja, v.id AS viesti_id, sisalto, lahetysaika, v.keskustelu AS keskustelu_id " +
        "FROM Viesti v INNER JOIN Kayttaja k ON (v.kirjoittaja = k.id) " + 
        "WHERE v.keskustelu = ?");
        stmt.setInt(1, keskustelu);

        ResultSet rs = stmt.executeQuery();
        ArrayList<Viesti> viestit = new ArrayList();
        while (rs.next()) {
   
            Integer kirjoittaja_id = rs.getInt("kirjoittaja_id");
            String kirjoittaja  = rs.getString("kirjoittaja");
            Integer viesti_id = rs.getInt("viesti_id");
            String sisalto = rs.getString("sisalto");
            Timestamp lahetysaika = rs.getTimestamp("lahetysaika");
            Integer keskustelu_id = rs.getInt("keskustelu_id");


            viestit.add(new Viesti(viesti_id, kirjoittaja_id, kirjoittaja, sisalto, lahetysaika, keskustelu_id));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
    }
    
    public List<Viesti> findPerAlue() throws SQLException {
        return new ArrayList<>();
    }
}
