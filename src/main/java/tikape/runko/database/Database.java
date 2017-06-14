package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

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

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

// tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
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
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 1, '2017-06-01 18:00', 'Testiviesti 1');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (2, 1, '2017-06-01 18:05', 'Testiviesti 2');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (3, 1, '2017-06-01 18:07', 'Testiviesti 3');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (4, 1, '2017-06-01 18:10', 'Testiviesti 4');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (2, 2, '2017-06-01 19:21', 'Keskustelualueiden listaus');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (3, 2, '2017-06-01 20:07', 'Keskustelujen listaus tietyllä keskustelualueella');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (4, 2, '2017-06-01 21:49', 'Viestien listaus tietyssä keskustelussa');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 3, '2017-06-01 23:50', 'Ponit on ihania!');");
        lista.add("INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 3, '2017-06-01 23:55', 'Eikö teistäkin!?!?!?');");

        return lista;
    }
}
