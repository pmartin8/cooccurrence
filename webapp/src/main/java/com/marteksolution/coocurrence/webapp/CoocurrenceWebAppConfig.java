package com.marteksolution.coocurrence.webapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.marteksolution.cooccurrence.database.DatabaseConfig;
import com.marteksolution.cooccurrence.service.WebCrawlerServiceConfig;

/**
 * Main configuration class for the Spring MVC web application.
 * 
 * 
 * @author Pierre Martin
 *
 */
@EnableWebMvc
@Configuration
@Import(value={DatabaseConfig.class, WebCrawlerServiceConfig.class})
@ComponentScan(basePackages = "com.marteksolution.cooccurrence")
public class CoocurrenceWebAppConfig {

	
}
