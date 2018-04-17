package hebsearch;

import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.jdbc.core.JdbcTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class Grocery_search_controller {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/search")
    @ResponseBody //basicaly tesl spring not to use a view
    public ArrayList<String> search(@RequestParam(name="serch_term", required=false, defaultValue="foo") String serch_term) {
      ArrayList<String> g_list = new ArrayList<String>();
      System.out.println("serch tem is"+ serch_term);
      //list of colems to iterate over
      String[] sql_col_array = ["id", "description", "lastSold", "shelfLife", "department", "price", "unit", "xFor", "cost"];
      //iterate to build a sql partal
      for(String coll : sql_col_array){
        System.out.println(coll);
      }
      //build sql
      sql_query = "SELECT * FROM grocery";
      //run

       jdbcTemplate.query(
               sql_query,
               (rs, rowNum) -> new Grocery_item(
                 rs.getString("id"),
                 rs.getString("description"),
                 rs.getString("lastSold"),
                 rs.getString("shelfLife"),
                 rs.getString("department"),
                 rs.getString("price"),
                 rs.getString("id"),
                 rs.getString("xFor"),
                 rs.getString("cost"))
       ).forEach(grocery_item -> g_list.add(grocery_item.toString()));
        //return list of grosery items
        return  g_list;
    }

}
