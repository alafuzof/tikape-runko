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

import java.util.Date;
        
public class Viesti {
    int kirjoittajaID;
    String kirjoittaja;
    int viestiID;
    String sisalto;
    Date lahetysaika;
    int keskustelu;

    public Viesti() {
        this.kirjoittajaID = -1;
        this.kirjoittaja = "Käyttäjä";
        this.viestiID = -1;
        this.keskustelu = -1;
        this.lahetysaika = new Date();
        this.sisalto = "Viesti";
    }

    public Viesti(int kirjoittajaID, String kirjoittaja, int viestiID, String sisalto, Date lahetysaika, int keskustelu) {
        this.kirjoittajaID = kirjoittajaID;
        this.kirjoittaja = kirjoittaja;
        this.viestiID = viestiID;
        this.sisalto = sisalto;
        this.lahetysaika = lahetysaika;
        this.keskustelu = keskustelu;
    }

    public int getKirjoittajaID() {
        return kirjoittajaID;
    }

    public int getKeskustelu() {
        return keskustelu;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public Date getLahetysaika() {
        return lahetysaika;
    }

    public String getSisalto() {
        return sisalto;
    }

    public int getViestiID() {
        return viestiID;
    }       
}
