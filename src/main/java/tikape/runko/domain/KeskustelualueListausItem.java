/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.Date;

/**
 *
 * @author artoo
 */
public class KeskustelualueListausItem implements Comparable<KeskustelualueListausItem>{
    
    private int rowindex;
    private Keskustelualue kalue;
    private int maara;
    private String viimeisin;
    
    public KeskustelualueListausItem(int rowindex, Keskustelualue kalue, int maara, String viimeisin) {
        this.rowindex = rowindex;
        this.kalue = kalue;
        this.maara = maara;
        this.viimeisin = viimeisin;
    }
    
    public String getNimi() {
        
        return this.kalue.getNimi();
    }

    public int getMaara() {
        
        return this.maara;
    }    
    
    public String getViimeisin() {
        
        return this.viimeisin;
    }
    public int getRowindex() {
        
        return this.rowindex;
    }
    @Override
    public int compareTo(KeskustelualueListausItem t) {
       if (this.rowindex > t.getRowindex()) {
            return 1;
        } else if (this.rowindex < t.getRowindex()) {
            return -1;
        } else {
            return 0;

        }    
    }
  
 


    
}
