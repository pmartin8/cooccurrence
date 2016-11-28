package com.marteksolution.cooccurrence.database.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marteksolution.cooccurrence.database.entity.Url;

/**
 * Standard repository for {@link Url} objects.
 * 
 * @author Pierre Martin
 *
 */
public interface UrlRepository extends JpaRepository<Url, Integer>{

	Url findFirstByDateScannedOrderByIdAsc(Date dateScanned);
	
	Url findByDescription(String description);
	
	List<Url> findByDateScanned(Date dateScanned);
}
