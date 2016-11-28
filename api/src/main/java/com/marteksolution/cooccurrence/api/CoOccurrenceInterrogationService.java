package com.marteksolution.cooccurrence.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Service to get information on word occurrences.
 * 
 * @author Pierre Martin
 *
 */
@Path("/cooccurrence/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CoOccurrenceInterrogationService {

}
