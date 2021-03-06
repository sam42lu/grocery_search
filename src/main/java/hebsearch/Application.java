package hebsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class Application implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(Application.class);

     private static final String groecry_csv_path = "./data.csv";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        log.info("starting to load data");
        //start reading in the CSV file
        Reader reader = Files.newBufferedReader(Paths.get(groecry_csv_path));
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> records = csvReader.readAll();

        //empty datat stucturs
        Boolean init_db = true;
        List<Object[]> batchlist = new ArrayList<>();
        String sql_perams = "";
        String sql_coll = "";

        //iterate over the csv to load data in to structurs
        for (String[] record : records) {
          if(init_db == true){
            //init db, and strings used fopor sql
            String coll_str="";
            for(String coll : record){
              coll_str = coll_str + " " + coll + " VARCHAR(255),";
              sql_perams = sql_perams + "?," ;
              sql_coll = sql_coll + coll + ", ";
            }
            //cleaning up autogenerated strings for SQL
            coll_str = coll_str.substring(0, coll_str.length() - 1);
            sql_perams = sql_perams.substring(0, sql_perams.length() - 1);
            sql_coll = sql_coll.substring(0, sql_coll.length() - 2);

            String create_table_str="CREATE TABLE grocery(" + coll_str + ")";
            jdbcTemplate.execute("DROP TABLE grocery IF EXISTS");
            jdbcTemplate.execute(create_table_str);
            init_db=false;
          }else{
              batchlist.add(record);
          }
        }

        //load the data in to the db
        String sql_write_str="INSERT INTO grocery(" + sql_coll + ") VALUES (" + sql_perams + ")";
        jdbcTemplate.batchUpdate(sql_write_str, batchlist);
    }
}
