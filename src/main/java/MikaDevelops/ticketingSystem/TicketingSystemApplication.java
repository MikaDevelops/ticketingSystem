package MikaDevelops.ticketingSystem;

import java.sql.Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class TicketingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketingSystemApplication.class, args);
                try (
                        Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:");
                        Statement statement = conn.createStatement();
                        )
                {
                    statement.executeUpdate("CREATE TABLE koetaulu (id INTEGER PRIMARY KEY, koe TEXT)");
                    statement.executeUpdate("INSERT INTO koetaulu (koe) VALUES ('kokkeilu')");
                    ResultSet result = statement.executeQuery("SELECT * FROM koetaulu");
                    while (result.next()){
                        System.out.println(result.getString("koe"));
                    }
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
	}

}
