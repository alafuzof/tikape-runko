CREATE TABLE Kayttaja
(
    id INTEGER PRIMARY KEY,
    tunnus VARCHAR(20) NOT NULL UNIQUE,
    salasana VARCHAR(20) DEFAULT NULL,
    admin BOOLEAN DEFAULT 0
);
 
CREATE TABLE Keskustelualue
(
    id INTEGER PRIMARY KEY,
    nimi VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Keskustelu
(
    id INTEGER PRIMARY KEY,
    aloittaja INTEGER NOT NULL,
    alue INTEGER NOT NULL,
    otsikko VARCHAR(100) NOT NULL,
    FOREIGN KEY(aloittaja) REFERENCES Kayttaja(id),
    FOREIGN KEY(alue) REFERENCES Keskustelualue(id)
);

CREATE TABLE Viesti
(
    id INTEGER PRIMARY KEY,
    kirjoittaja INTEGER NOT NULL,
    keskustelu INTEGER NOT NULL,
    lahetysaika TIMESTAMP NOT NULL,
    sisalto VARCHAR(500) NOT NULL,
    FOREIGN KEY(kirjoittaja) REFERENCES Kayttaja(id),
    FOREIGN KEY(keskustelu) REFERENCES Keskustelu(id)
);

CREATE VIEW KeskustelualueList AS 
SELECT a.id, a.nimi, COUNT(v.id) as maara, MAX(lahetysaika) AS viimeisin 
FROM Keskustelualue a 
LEFT JOIN Keskustelu k ON (a.id = k.alue) 
LEFT JOIN Viesti v ON (k.id = v.keskustelu) 
GROUP by a.id, a.nimi;