package sharifullinruzil.crudapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("sharifullinruzil.crudapp.repository")
@PropertySource("classpath:postgres.properties")
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean("dataSource")
    DriverManagerDataSource dataSource(){
        return new DriverManagerDataSource(env.getProperty("database.url"), env.getProperty("database.username"), env.getProperty("database.password"));
    }
}
