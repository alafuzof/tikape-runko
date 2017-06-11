package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskusteluDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        KeskusteluDao keskusteluDao = new KeskusteluDao(database);

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
