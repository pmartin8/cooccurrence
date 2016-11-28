package com.marteksolution.cooccurrence.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marteksolution.cooccurrence.database.entity.Word;

/**
 * Standard repository for {@link Word} objects.
 * 
 * @author Pierre Martin
 *
 */
public interface WordRepository extends JpaRepository<Word, Integer>{
	
	Word findByDescription(String description);
}
