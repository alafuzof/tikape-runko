/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author tikape
 */
public class Kayttaja {
    Integer kayttajaID;
    String tunnus;

    public Kayttaja(Integer kayttajaID, String tunnus) {
        this.kayttajaID = kayttajaID;
        this.tunnus = tunnus;
    }
    
    public Kayttaja(String tunnus) {
        this.kayttajaID = -1;
        this.tunnus = tunnus;
    }

    public String getTunnus() {
        return tunnus;
    }    

    public Integer getKayttajaID() {
        return kayttajaID;
    }
    
    
}
