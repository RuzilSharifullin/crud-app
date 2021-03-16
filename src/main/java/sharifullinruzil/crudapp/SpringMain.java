package sharifullinruzil.crudapp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

public class SpringMain {

    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        DriverManagerDataSource dataSource = applicationContext.getBean(DriverManagerDataSource.class);
        boolean valid = dataSource.getConnection().isValid(2000);
        System.out.println(valid);
    }
}
