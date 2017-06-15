
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author artoo
 */


public class Keskustelualue {

    private Integer id;
    private String nimi;

    public Keskustelualue(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }


    public Keskustelualue(String nimi) {
        this.id = null;
        this.nimi = nimi;
    }    
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    
}

