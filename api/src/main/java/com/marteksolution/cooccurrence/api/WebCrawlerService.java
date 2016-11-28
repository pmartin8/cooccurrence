package com.marteksolution.cooccurrence.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Service to interact with the web crawler.
 * 
 * @author Pierre Martin
 *
 */
@Path("/webcrawler/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface WebCrawlerService {

	/**
	 * Start the web crawler.
	 * 
	 * @return "Started" literal.
	 */
	@POST
	@Path("start")
	public String start();
	
	/**
	 * Stop the web crawler.
	 * 
	 * @return "Stopped" literal.
	 */
	@POST
	@Path("stop")
	public String stop();
}
