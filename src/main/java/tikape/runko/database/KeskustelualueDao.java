
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

import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.KeskustelualueListausItem;


/**
 *
 * @author artoo
 */
public class KeskustelualueDao implements Dao<Keskustelualue, Integer>{

    private Database database;

    public KeskustelualueDao(Database database) {
        this.database = database;
    }

    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    
  @Override
    public Keskustelualue add(Keskustelualue instance) throws SQLException {
        // IMPLEMENTOI
        return null;
    }
    
    @Override
    public  Keskustelualue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM  Keskustelualue WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");


        Keskustelualue o = new Keskustelualue(id, nimi);

         Keskustelualue keskustelualuex = new  Keskustelualue(id,nimi);


            rs.close();
            stmt.close();
            connection.close();
        return o;
    }


    @Override
    public List< Keskustelualue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue");

        ResultSet rs = stmt.executeQuery();

        List<Keskustelualue> keskustelualueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            keskustelualueet.add(new Keskustelualue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelualueet;    
    }

    public List<KeskustelualueListausItem> findAllForList() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM KeskustelualueList ORDER BY viimeisin DESC");

        ResultSet rs = stmt.executeQuery();
        List<KeskustelualueListausItem> keskustelualuelistaus = new ArrayList<>();
        int i = 0;
        while (rs.next()) {
            Integer rowindex = i;
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            Keskustelualue kalue = new Keskustelualue(id, nimi);
            Integer maara = rs.getInt("maara");
            String viimeisin = rs.getString("viimeisin");            

            keskustelualuelistaus.add(new KeskustelualueListausItem(rowindex, kalue ,maara, viimeisin));
            i++;
        }
        
        rs.close();
        stmt.close();
        connection.close();

        return keskustelualuelistaus;    
    }    


    public void save(Keskustelualue kalue) throws SQLException {
        this.database.update("INSERT INTO Keskustelualue(nimi) VALUES (?)", kalue.getNimi());
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        this.database.update("DELETE FROM Keskustelualue WHERE id = ?", key);        
    }


    
}

