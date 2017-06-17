package tikape.runko;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Keskustelualue;
//import tikape.runko.domain.KeskustelualueListausItem;


public class Main {

    public static void main(String[] args) throws Exception {

        Database database = new Database("jdbc:sqlite:testi.db");
        database.init();

        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        
        ViestiDao viestiDao = new ViestiDao(database);
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
                

        get("/index", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("keskustelualueet", keskustelualueDao.findAll());
            
            //System.out.println(keskustelualueDao.findAll().size());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/testi.html", (req, res) -> {
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

        // Here comes the missing DAO-modules calls:
        get("/keskustelualueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelualueet", keskustelualueDao.findAll());

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

        get("/:alue/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", req.params("alue"));
            map.put("keskustelut", keskusteluDao.findPerAlue(req.params("alue")));
            
            
            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());
        
        get("/:alue/:keskustelu", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", req.params("alue"));
            map.put("otsikko", "PLACEHOLDER");
            map.put("viestit", viestiDao.findAllByKeskustelu(Integer.parseInt(req.params("keskustelu"))));
            //map.put("keskustelut", keskusteluDao.findPerAlue(req.params("alue")));
            
            
            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        
        post("/:alue/:keskustelu", (req, res) -> {
            String kirjoittaja = req.queryParams("kirjoittaja");
            String viesti = req.queryParams("viesti");
            System.out.println("Kirjoittaja: " + kirjoittaja + " Viesti: " + viesti);
            
            res.redirect("/" + req.params("alue") + "/" + req.params("keskustelu"));
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
