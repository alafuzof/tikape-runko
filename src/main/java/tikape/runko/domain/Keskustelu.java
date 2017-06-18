package tikape.runko.domain;

import java.sql.Timestamp;

public class Keskustelu {

    private Integer id;
    private Integer aloittaja;
    private Integer alue;
    private String otsikko;
    private int viestimaara;
    private Timestamp avausaika;

    public Keskustelu(Integer id, Integer aloittaja, Integer alue, String otsikko) {
        this.id = id;
        this.aloittaja = aloittaja;
        this.alue = alue;
        this.otsikko = otsikko;
        this.viestimaara = viestimaara;
        this.avausaika = avausaika;
    }

    public Keskustelu(Integer aloittaja, Integer alue, String otsikko) {
        this.id = null;
        this.aloittaja = aloittaja;
        this.alue = alue;
        this.otsikko = otsikko;
        this.viestimaara = 0;
        this.avausaika = null;
    }    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAloittaja() {
        return aloittaja;
    }

    public void setAloittaja(Integer aloittaja) {
        this.aloittaja = aloittaja;
    }

    public Integer getAlue() {
        return alue;
    }

    public void setAlue(Integer alue) {
        this.alue = alue;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    public Timestamp getAvausaika() {
        return avausaika;
    }
    public void setAvausaika(Timestamp avausaika) {
        this.avausaika = avausaika;
    }
    
    public int getViestimaara() {
        return viestimaara;
    }

    public void setViestimaara(Integer viestimaara) {
        this.viestimaara = viestimaara;
    }
}
