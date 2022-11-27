package org.demoApp.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.demoApp")
@PropertySource("classpath:persistance-postgre.properties")
public class DemoAppConfig {

    private final Environment env;

    //set up a logger via diagnostics
    Logger logger = Logger.getLogger(getClass().getName());

    public DemoAppConfig(final Environment env) {
        this.env = env;
    }

    // define a bean for ViewResolver
    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    //define a bean for our security datasource

    @Bean
    public DataSource securityDatasource() {
        //create connection pool
        final ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

        //set the jdbc driver class
        try {
            securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        //log the connection props

        //just to make sure we are REALLY reading data from properties file
        logger.info(">>> jdbc.url= " + env.getProperty("jdbc.url"));
        logger.info(">>> jdbc.user= " + env.getProperty("jdbc.user"));
        //set db connection props

        securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        securityDataSource.setUser(env.getProperty("jdbc.user"));
        securityDataSource.setPassword(env.getProperty("jdbc.password"));
        //set connection pool props

        securityDataSource.setInitialPoolSize(
                getIntProperty("connection.pool.initialPoolSize"));
        securityDataSource.setMinPoolSize(
                getIntProperty("connection.pool.minPoolSize"));
        securityDataSource.setMaxPoolSize(
                getIntProperty("connection.pool.maxPoolSize"));
        securityDataSource.setMaxIdleTime(
                getIntProperty("connection.pool.maxIdleTime"));

        return securityDataSource;
    }

    //read environment props and convert to int
    private int getIntProperty(final String propName) {
        final String propVal = env.getProperty(propName);

        assert null != propVal;
        return Integer.parseInt(propVal);
    }
}









