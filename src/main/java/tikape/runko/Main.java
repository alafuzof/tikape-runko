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
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.KeskustelualueListausItem;


public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:testi.db");
        database.init();

        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        
        // Tulostetaan keskustelualueet
        List<Keskustelualue> keskustelualueet = new ArrayList<>();
        
        keskustelualueet = keskustelualueDao.findAll();
        
        System.out.println("Keskustelualueet");
        for (Keskustelualue k : keskustelualueet) {
            
            System.out.println(k.getNimi());
            
        }
        
        // Tulostetaan keskustelualuelistaus niinkuin tehtäväannossa
        System.out.println("");
        System.out.print("*********************");
        System.out.println("Keskustelualuelistaus");

        List<KeskustelualueListausItem> keskustelualuelistaus = new ArrayList<>();
        
        keskustelualuelistaus = keskustelualueDao.findAllForList();
        Collections.sort(keskustelualuelistaus);
        
        System.out.println("Keskustelualueet");
        for (KeskustelualueListausItem k : keskustelualuelistaus) {
            
            System.out.println(k.getNimi() +"\t"+ k.getMaara() + "\t" + k.getViimeisin());
            
        }
                

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "moikka");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/keskustelut", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelut", keskusteluDao.findAll());

            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());

        get("/keskustelut/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelu", keskusteluDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        
    }
}
