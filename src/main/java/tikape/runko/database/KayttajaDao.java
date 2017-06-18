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
import java.util.List;
import tikape.runko.domain.Kayttaja;

/**
 *
 * @author tikape
 */
public class KayttajaDao implements Dao<Kayttaja, String> {
    private Database database;
    
    public KayttajaDao(Database database) {
        this.database = database;
    }

    @Override
    public void delete(String tunnus) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Kayttaja add(Kayttaja k) throws SQLException {
        Connection connection = this.database.getConnection();
        
        
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Kayttaja (tunnus) VALUES (?)");
        stmt.setString(1, k.getTunnus());
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
        return this.findOne(k.getTunnus());
    }

    @Override
    public Kayttaja findOne(String tunnus) throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja WHERE tunnus = ?");
        stmt.setString(1, tunnus);
        
        ResultSet rs = stmt.executeQuery();
        Kayttaja tulos = null;
        if(rs.next()) {
            tulos = new Kayttaja(rs.getInt("id"), rs.getString("tunnus"));
        }       
        
        rs.close();
        stmt.close();
        connection.close();
        return tulos;
    }

    @Override
    public List<Kayttaja> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
