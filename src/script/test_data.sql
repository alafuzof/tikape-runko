INSERT INTO Kayttaja (id, tunnus, salasana, admin) VALUES (0, 'admin', 'admin', 1);
INSERT INTO Kayttaja (id, tunnus) VALUES (1, 'Aleksander');
INSERT INTO Kayttaja (id, tunnus) VALUES (2, 'Arto');
INSERT INTO Kayttaja (id, tunnus) VALUES (3, 'Timo');
INSERT INTO Kayttaja (id, tunnus) VALUES (4, 'Sara');

INSERT INTO Keskustelualue (id, nimi) VALUES (1, 'Tietokannat');
INSERT INTO Keskustelualue (id, nimi) VALUES (2, 'Ponit');

INSERT INTO Keskustelu (id, aloittaja, alue, otsikko) VALUES (1, 2, 1, 'Tikape kotitehtävät');
INSERT INTO Keskustelu (id, aloittaja, alue, otsikko) VALUES (2, 3, 1, 'Käyttötapaukset');
INSERT INTO Keskustelu (id, aloittaja, alue, otsikko) VALUES (3, 1, 2, 'Mikä on sun lempiponi?');

INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 1, '2017-06-01 18:00', 'Testiviesti 1');
INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (2, 1, '2017-06-01 18:05', 'Testiviesti 2');
INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (3, 1, '2017-06-01 18:07', 'Testiviesti 3');
INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (4, 1, '2017-06-01 18:10', 'Testiviesti 4');

INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (2, 2, '2017-06-01 19:21', 'Keskustelualueiden listaus');
INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (3, 2, '2017-06-01 20:07', 'Keskustelujen listaus tietyllä keskustelualueella');
INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (4, 2, '2017-06-01 21:49', 'Viestien listaus tietyssä keskustelussa');

INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 3, '2017-06-01 23:50', 'Ponit on ihania!');
INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 3, '2017-06-01 23:55', 'Eikö teistäkin!?!?!?');
INSERT INTO Viesti (kirjoittaja, keskustelu, lahetysaika, sisalto) VALUES (1, 3, '2017-06-01 23:56', 'Eiks kukaan muu tykkää poneista :(');


