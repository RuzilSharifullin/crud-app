package sharifullinruzil.crudapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import sharifullinruzil.crudapp.config.AppConfig;

import javax.sql.DataSource;

@Configuration
@Import(AppConfig.class)
public class TestConfig {

    @Autowired
    DataSource dataSource;
    @Value("classpath:populateDB.sql")
    private Resource schemaScript;
    @Value("classpath:initDB.sql")
    private Resource dataScript;

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(dataScript);
        populator.addScript(schemaScript);
        return populator;
    }
}
