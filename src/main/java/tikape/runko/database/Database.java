package tikape.runko.database;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
        
        init();
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }
    
    public int update(String updateQuery, Object... params) throws SQLException {
        PreparedStatement stmt = this.getConnection().prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();
        /*
        if (debug) {
            System.out.println("---");
            System.out.println(updateQuery);
            System.out.println("Changed rows: " + changes);
            System.out.println("---");
        }
        */
        stmt.close();

        return changes;
    }    
        
    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();
        
        lista.add("CREATE TABLE kayttaja(id SERIAL PRIMARY KEY, tunnus VARCHAR(20) NOT NULL UNIQUE, salasana VARCHAR(20) DEFAULT null, admin BOOLEAN DEFAULT false);");
        lista.add("INSERT INTO kayttaja (tunnus, salasana, admin) VALUES ('admin', 'admin', true);");
        lista.add("INSERT INTO kayttaja (tunnus) VALUES ('Aleksander');");
        lista.add("INSERT INTO kayttaja (tunnus) VALUES ('Arto');");
        lista.add("INSERT INTO kayttaja (tunnus) VALUES ('Timo');");
        lista.add("INSERT INTO kayttaja (tunnus) VALUES ('Sara');");
        
        lista.add("CREATE TABLE keskustelualue(id SERIAL PRIMARY KEY, nimi VARCHAR(100) NOT NULL UNIQUE)");
        lista.add("INSERT INTO keskustelualue (nimi) VALUES ('Tietokannat');");
        lista.add("INSERT INTO keskustelualue (nimi) VALUES ('Ponit');");
        
        lista.add("CREATE TABLE keskustelu(id SERIAL PRIMARY KEY, aloittaja INTEGER NOT NULL, alue INTEGER NOT NULL, otsikko VARCHAR(100) NOT NULL, FOREIGN KEY (aloittaja) REFERENCES kayttaja(id), FOREIGN KEY (alue) REFERENCES keskustelualue(id));");
        lista.add("INSERT INTO keskustelu (aloittaja, alue, otsikko) VALUES (2, 1, 'Tikape kotitehtävät');");
        lista.add("INSERT INTO keskustelu (aloittaja, alue, otsikko) VALUES (3, 1, 'Käyttötapaukset');");
        lista.add("INSERT INTO keskustelu (aloittaja, alue, otsikko) VALUES (1, 2, 'Mikä on sun lempiponi?');");

        lista.add("CREATE TABLE viesti(id SERIAL PRIMARY KEY, kirjoittaja INTEGER NOT NULL, keskustelu INTEGER NOT NULL, lahetysaika TIMESTAMP NOT NULL, sisalto VARCHAR(500) NOT NULL, FOREIGN KEY(kirjoittaja) REFERENCES kayttaja(id), FOREIGN KEY(keskustelu) REFERENCES keskustelu(id));");
        lista.add("INSERT INTO viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 1, '2017-06-01 18:00:00.000', 'Testiviesti 1');");
        lista.add("INSERT INTO viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (2, 1, '2017-06-01 18:05:00.000', 'Testiviesti 2');");
        lista.add("INSERT INTO viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (3, 1, '2017-06-01 18:07:00.000', 'Testiviesti 3');");
        lista.add("INSERT INTO viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (4, 1, '2017-06-01 18:10:00.000', 'Testiviesti 4');");
        lista.add("INSERT INTO viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (2, 2, '2017-06-01 19:21:00.000', 'Keskustelualueiden listaus');");
        lista.add("INSERT INTO viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (3, 2, '2017-06-01 20:07:00.000', 'Keskustelujen listaus tietyllä keskustelualueella');");
        lista.add("INSERT INTO viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (4, 2, '2017-06-01 21:49:00.000', 'Viestien listaus tietyssä keskustelussa');");
        lista.add("INSERT INTO viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 3, '2017-06-01 23:50:00.000', 'Ponit on ihania!');");
        lista.add("INSERT INTO viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 3, '2017-06-01 23:55:00.000', 'Eikö teistäkin!?!?!?');");
        
        lista.add("CREATE VIEW KeskustelualueList AS "
        + "SELECT a.id, a.nimi, COUNT(v.id) as maara, MAX(lahetysaika) AS viimeisin "
        + "FROM Keskustelualue a "
        + "LEFT JOIN Keskustelu k ON (a.id = k.alue) "
        + "LEFT JOIN Viesti v ON (k.id = v.keskustelu) "
        + "GROUP by a.id, a.nimi;");

        lista.add("CREATE VIEW KeskusteluList AS "
        + "SELECT k.id AS id, k.otsikko, k.aloittaja, k.alue AS alue, "
        + "COUNT(*) AS viestimaara, MIN(v.lahetysaika) AS avausaika, "
        + "MAX(v.lahetysaika) AS viimeisin FROM Keskustelu k "
        + "LEFT JOIN Viesti v ON (k.id = v.keskustelu) "
        + "GROUP BY k.id, k.otsikko, k.aloittaja, k.alue "
        + "ORDER BY MAX(v.lahetysaika) DESC;");
        
        return lista;
    }
    
    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

// tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
/*
        lista.add("CREATE TABLE Kayttaja(id INTEGER PRIMARY KEY, tunnus VARCHAR(20) NOT NULL UNIQUE, salasana VARCHAR(20) DEFAULT NULL, admin BOOLEAN DEFAULT 0);");
        lista.add("INSERT INTO Kayttaja (id, tunnus, salasana, admin) VALUES (0, 'admin', 'admin', 1);");
        lista.add("INSERT INTO Kayttaja (id, tunnus) VALUES (1, 'Aleksander');");
        lista.add("INSERT INTO Kayttaja (id, tunnus) VALUES (2, 'Arto');");
        lista.add("INSERT INTO Kayttaja (id, tunnus) VALUES (3, 'Timo');");
        lista.add("INSERT INTO Kayttaja (id, tunnus) VALUES (4, 'Sara');");
        
        lista.add("CREATE TABLE Keskustelualue(id INTEGER PRIMARY KEY, nimi VARCHAR(100) NOT NULL UNIQUE)");
        lista.add("INSERT INTO Keskustelualue (id, nimi) VALUES (1, 'Tietokannat');");
        lista.add("INSERT INTO Keskustelualue (id, nimi) VALUES (2, 'Ponit');");
            
        lista.add("CREATE TABLE Keskustelu(id INTEGER PRIMARY KEY, aloittaja INTEGER NOT NULL, alue INTEGER NOT NULL, otsikko VARCHAR(100) NOT NULL, FOREIGN KEY(aloittaja) REFERENCES Kayttaja(id), FOREIGN KEY(alue) REFERENCES Keskustelualue(id));");
        lista.add("INSERT INTO Keskustelu (id, aloittaja, alue, otsikko) VALUES (1, 2, 1, 'Tikape kotitehtävät');");
        lista.add("INSERT INTO Keskustelu (id, aloittaja, alue, otsikko) VALUES (2, 3, 1, 'Käyttötapaukset');");
        lista.add("INSERT INTO Keskustelu (id, aloittaja, alue, otsikko) VALUES (3, 1, 2, 'Mikä on sun lempiponi?');");

        lista.add("CREATE TABLE Viesti(id INTEGER PRIMARY KEY, kirjoittaja INTEGER NOT NULL, keskustelu INTEGER NOT NULL, lahetysaika TIMESTAMP NOT NULL, sisalto VARCHAR(500) NOT NULL, FOREIGN KEY(kirjoittaja) REFERENCES Kayttaja(id), FOREIGN KEY(keskustelu) REFERENCES Keskustelu(id));");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 1, '2017-06-01 18:00:00.000', 'Testiviesti 1');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (2, 1, '2017-06-01 18:05:00.000', 'Testiviesti 2');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (3, 1, '2017-06-01 18:07:00.000', 'Testiviesti 3');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (4, 1, '2017-06-01 18:10:00.000', 'Testiviesti 4');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (2, 2, '2017-06-01 19:21:00.000', 'Keskustelualueiden listaus');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (3, 2, '2017-06-01 20:07:00.000', 'Keskustelujen listaus tietyllä keskustelualueella');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (4, 2, '2017-06-01 21:49:00.000', 'Viestien listaus tietyssä keskustelussa');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 3, '2017-06-01 23:50:00.000', 'Ponit on ihania!');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 3, '2017-06-01 23:55:00.000', 'Eikö teistäkin!?!?!?');");

        lista.add("CREATE VIEW KeskustelualueList AS "
        + "SELECT a.id, a.nimi, COUNT(v.id) as maara, MAX(lahetysaika) AS viimeisin "
        + "FROM Keskustelualue a "
        + "LEFT JOIN Keskustelu k ON (a.id = k.alue) "
        + "LEFT JOIN Viesti v ON (k.id = v.keskustelu) "
        + "GROUP by a.id, a.nimi;");

        lista.add("CREATE VIEW KeskusteluList AS "
        + "SELECT k.id AS id, k.otsikko, k.aloittaja, k.alue AS alue, "
        + "COUNT(*) AS viestimaara, MIN(v.lahetysaika) AS avausaika, "
        + "MAX(v.lahetysaika) AS viimeisin FROM Keskustelu k "
        + "LEFT JOIN Viesti v ON (k.id = v.keskustelu) "
        + "GROUP BY k.id, k.otsikko, k.aloittaja, k.alue "
        + "ORDER BY MAX(v.lahetysaika) DESC;");
 */       
        return lista;
    }
}
