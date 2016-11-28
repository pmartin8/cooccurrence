package com.marteksolution.coocurrence.webapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Initializer for the Spring MVC web application.
 * 
 * 
 * @author Pierre Martin
 *
 */
public class CoocurrenceWebAppInitializer implements WebApplicationInitializer {
	
	private static final Logger LOGGER = Logger.getLogger(CoocurrenceWebAppInitializer.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
        
        LOGGER.info("**** Co-occurrence Webapp starting... ****");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.marteksolution.coocurrence.webapp");
        return context;
    }


}
