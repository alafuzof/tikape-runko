package tikape.runko.domain;

public class Keskustelu {

    private Integer id;
    private String otsikko;

    public Keskustelu(Integer id, String otsikko) {
        this.id = id;
        this.otsikko = otsikko;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

}
