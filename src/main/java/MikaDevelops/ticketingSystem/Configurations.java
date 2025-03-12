package MikaDevelops.ticketingSystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {

    private final String dbAddress = "testing.db";

    @Bean(name="dataBaseAddress")
    public String returnAddress(){
        return dbAddress;
    }
}
