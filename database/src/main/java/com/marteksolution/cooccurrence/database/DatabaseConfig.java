package com.marteksolution.cooccurrence.database;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

/**
 * 
 * @author Pierre Martin
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.marteksolution.cooccurrence.database.repository"})
@EnableTransactionManagement
@PropertySource(value={"classpath:application.properties"})
public class DatabaseConfig {
	
	@Resource
	private DataSource dataSource;
	
	@Value("${hibernate.dialect}")
	private String dialect;
	
	@Value("${hibernate.show_sql}")
	private boolean showSql;
	
	@Value("${entitymanager.packages.to.scan}")
	private String packageToScan;
	
	@Bean
	public DataSource dataSource() {
	    JndiDataSourceLookup dataSource = new JndiDataSourceLookup();
	    dataSource.setResourceRef(true);
	    return dataSource.getDataSource("jdbc/dictionary");
	}
	
	@Bean
	public SpringLiquibase liquibase() {
	    SpringLiquibase liquibase = new SpringLiquibase();

	    liquibase.setDataSource(dataSource);
	    liquibase.setChangeLog("classpath:liquibase/liquibase.xml");
	    liquibase.setShouldRun(true);

	    return liquibase;
	}
	
	@Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource());
        lef.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        lef.setPackagesToScan(packageToScan);
        lef.afterPropertiesSet();
        
        Properties prop = new Properties();
        prop.put("hibernate.dialect", dialect);
        prop.put("hibernate.show_sql", showSql);
        lef.setJpaProperties(prop);
        return lef.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}
