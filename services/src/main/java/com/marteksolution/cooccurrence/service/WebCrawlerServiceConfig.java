package com.marteksolution.cooccurrence.service;

import java.util.Arrays;
import java.util.LinkedList;

import javax.ws.rs.Path;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marteksolution.cooccurrence.database.entity.Language;
import com.marteksolution.cooccurrence.database.repository.LanguageRepository;

/**
 * Configuration of the web crawler.
 * 
 * @author Pierre Martin
 *
 */
@Configuration
public class WebCrawlerServiceConfig {
	
	private static final short ID_FRENCH = 1;
	
	@Autowired
    private ApplicationContext ctx;

	@Autowired
	private LanguageRepository languageRepository;
	
	
	@Bean
	public Language language() {
		return languageRepository.findOne(ID_FRENCH);
	}
	
	@Bean(name="cxf")
	public SpringBus springBus(){
		return new SpringBus();
	}

    @Bean
    public Server jaxRsServer() {
        LinkedList<ResourceProvider> resourceProviders = new LinkedList<ResourceProvider>();
        for (String beanName : ctx.getBeanDefinitionNames()) {
            if (ctx.findAnnotationOnBean(beanName, Path.class) != null) {
                SpringResourceFactory factory = new SpringResourceFactory(beanName);
                factory.setApplicationContext(ctx);
                resourceProviders.add(factory);
            }
        }

        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setBus(ctx.getBean(SpringBus.class));
        factory.setProviders(Arrays.asList(new JacksonJsonProvider()));
        factory.setResourceProviders(resourceProviders);
        return factory.create();
    }
	
}
