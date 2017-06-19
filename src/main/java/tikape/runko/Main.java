package tikape.runko;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KayttajaDao;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Kayttaja;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.KeskustelualueListausItem;


public class Main {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:testi.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        } 
        Database database = new Database(jdbcOsoite);
        //Database database = new Database("jdbc:sqlite:testi.db");
        database.init();

        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        
        ViestiDao viestiDao = new ViestiDao(database);
        
        KayttajaDao kayttajaDao = new KayttajaDao(database);
        /*
        // Tulostetaan keskustelualueet
        List<Keskustelualue> keskustelualueet = new ArrayList<>();
        
        keskustelualueet = keskustelualueDao.findAll();
        
        System.out.println("Keskustelualueet");
        for (Keskustelualue k : keskustelualueet) {
            
            System.out.println(k.getNimi());
            
        }
        */
        // Tulostetaan keskustelualuelistaus niinkuin tehtäväannossa
        /*System.out.println("");
        System.out.print("*********************");
        System.out.println("Keskustelualuelistaus");

        /*List<KeskustelualueListausItem> keskustelualuelistaus = new ArrayList<>();
        
        keskustelualuelistaus = keskustelualueDao.findAllForList();
        Collections.sort(keskustelualuelistaus);
        
        System.out.println("Keskustelualueet");
        for (KeskustelualueListausItem k : keskustelualuelistaus) {
            
            System.out.println(k.getNimi() +"\t"+ k.getMaara() + "\t" + k.getViimeisin());
            
        }*/
                

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("keskustelualueet", keskustelualueDao.findAllForList());
            
            //System.out.println(keskustelualueDao.findAllForList());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        /*get("/testi.html", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "Tervetuloa AHOT-foorumille !");

            return new ModelAndView(map, "testi");
        }, new ThymeleafTemplateEngine());

        get("/keskustelut", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelut", keskusteluDao.findAll());

            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());

        get("/keskustelu/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelu", keskusteluDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        
        post("/keskustelualue", (req, res) -> {

            String kayttaja = req.queryParams("kayttaja");
            //String keskustelualue = req.queryParams("keskustelualue");
            String keskustelu = req.queryParams("keskustelun otsikko");
                   

            System.out.println("Kirjoittaja: " + kayttaja + " Keskustelualue: " + keskustelualue);
            
            Kayttaja k = kayttajaDao.findOne(kayttaja);
            if(k == null) {
                System.out.println("Luodaan uusi käyttäjä!");
                k = kayttajaDao.add(new Kayttaja(kayttaja));
            }

            
            System.out.println("Luodaan uusi keskustelu!");

            
            int keskustelualueid = keskusteluDao.findOne(Integer.parseInt(req.params("keskustelualue"))).getId();
            
            keskusteluDao.add(new Keskustelu(k.getKayttajaID(), keskustelualueid, keskustelu));
            
            res.redirect("/" + req.params("alue") + "/" + req.params("keskustelu"));
            
            
            return "";
        });
        */

        /* Näitä ei välttämättä tarvita lopullisessa versiossa

        get("/keskustelualueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelualueet", keskustelualueDao.findAllForList());

            return new ModelAndView(map, "keskustelualueet");
        }, new ThymeleafTemplateEngine());

        get("/keskustelutPerAlue", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelut", keskusteluDao.findPerAlue("Tietokannat"));
            //System.out.println("" + keskusteluDao.findPerAlue("Tietokannat").size() + " keskustelua");
            return new ModelAndView(map, "keskustelutPerAlue");
        }, new ThymeleafTemplateEngine());

        get("/keskustelualue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelualue", keskustelualueDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());
        */
        
        // Tämä GET näyttää kaikki tietyn keskustelualueen keskuteluotsikot
        get("/:alue/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", req.params("alue"));
            map.put("keskustelut", keskusteluDao.findPerAlue(req.params("alue")));
            
            
            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());
        
        // Tämä GET näyttää tietyn keskustelun viestit
        get("/:alue/:keskustelu", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", req.params("alue"));
            map.put("otsikko", keskusteluDao.findOne(Integer.parseInt(req.params("keskustelu"))).getOtsikko());
            map.put("viestit", viestiDao.findAllByKeskustelu(Integer.parseInt(req.params("keskustelu"))));
            //map.put("keskustelut", keskusteluDao.findPerAlue(req.params("alue")));
            
            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        
        // Tämä POST käsittelee viestin lähetyksen olemassa olevaan keskusteluun
        // älä muuta tai kommentoi tätä pois!
        post("/:alue/:keskustelu", (req, res) -> {
            String kirjoittaja = req.queryParams("kirjoittaja");
            String viesti = req.queryParams("viesti");
            System.out.println("Kirjoittaja: " + kirjoittaja + " Viesti: " + viesti);
            
            Kayttaja k = kayttajaDao.findOne(kirjoittaja);
            if(k == null) {
                System.out.println("Luodaan uusi käyttäjä!");
                k = kayttajaDao.add(new Kayttaja(kirjoittaja));
            }
                
            int keskustelu = keskusteluDao.findOne(Integer.parseInt(req.params("keskustelu"))).getId();
            
            viestiDao.add(new Viesti(k.getKayttajaID(), k.getTunnus(), keskustelu, viesti));

            
            res.redirect("/" + req.params("alue") + "/" + req.params("keskustelu"));
            return "";
        });

        // Tämä POST luo uuden keskustelun jollekin keskustelualueelle
        post("/:alue/",  (req, res) -> {
            String kayttaja = req.queryParams("kirjoittaja");
            //String keskustelualue = req.queryParams("keskustelualue");
            String otsikko = req.queryParams("otsikko");
            //System.out.println("Kirjoittaja: " + kayttaja + " Keskustelualue: " + keskustelualue);

            
            Kayttaja k = kayttajaDao.findOne(kayttaja);
            if(k == null) {
                System.out.println("Luodaan uusi käyttäjä!");
                k = kayttajaDao.add(new Kayttaja(kayttaja));
            }

            
            System.out.println("Luodaan uusi keskustelu!");
            //Keskustelu kes = keskusteluDao.add(new Keskustelu(Integer.parseInt(req.params("id")), Integer.parseInt(req.params("aloittaja")), Integer.parseInt(req.params("alue")), req.params("otsikko")));
            //Keskustelu newKesk = keskusteluDao.add(new Keskustelu(Integer.parseInt(req.params("keskustelu")), k.getKayttajaID(), Integer.parseInt(req.params("alue")), req.params("otsikko")));
            
            
            //kesk = keskusteluDao.findOne(Integer.parseInt(req.params("keskustelu")));
            
            //res.redirect("/" + newKesk.getOtsikko() + "/" + Integer.toString (newKesk.getId()));
            
            int alueID = keskustelualueDao.findOneByNimi(req.params("alue")).getId();
            
            keskusteluDao.add(new Keskustelu(k.getKayttajaID(), alueID, otsikko));
            
            res.redirect("/" + req.params("alue") + "/");
            
            return "";

        });        

        
//        get("/viestit", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("viestit", viestiDao.findAll());
//
//            return new ModelAndView(map, "viestit");
//        }, new ThymeleafTemplateEngine());



    }
}
