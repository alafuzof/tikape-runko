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

import java.sql.Timestamp;
import java.util.Date;
        
public class Viesti {
    int viestiID;
    int kirjoittajaID;
    String kirjoittaja;
    String sisalto;
    Timestamp lahetysaika;
    int keskustelu;

    public Viesti() {
        this.viestiID = -1;
        this.kirjoittajaID = -1;
        this.kirjoittaja = "Käyttäjä";
        
        this.keskustelu = -1;
        Date date = new Date();
        this.lahetysaika = new Timestamp(date.getTime());
        this.sisalto = "Viesti";
    }
    
    public Viesti(int kirjoittajaID, String kirjoittaja, int keskustelu, String sisalto) {
        this.viestiID = -1;
        this.kirjoittajaID = kirjoittajaID;
        this.kirjoittaja = kirjoittaja;
        this.keskustelu = keskustelu;
        Date date = new Date();
        this.lahetysaika = new Timestamp(date.getTime());
        this.sisalto = sisalto;
    }

    public Viesti(int viestiID, int kirjoittajaID, String kirjoittaja, String sisalto, Timestamp lahetysaika, int keskustelu) {
        this.viestiID = viestiID;
        this.kirjoittajaID = kirjoittajaID;
        this.kirjoittaja = kirjoittaja;
        this.keskustelu = keskustelu;
        this.lahetysaika = lahetysaika;
        this.sisalto = sisalto;
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

    public Timestamp getLahetysaika() {
        return lahetysaika;
    }

    public String getSisalto() {
        return sisalto;
    }

    public int getViestiID() {
        return viestiID;
    }       
}
