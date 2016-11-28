package com.marteksolution.cooccurrence.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marteksolution.cooccurrence.database.entity.SentenceOccurrence;
import com.marteksolution.cooccurrence.database.entity.SentenceOccurrenceId;

/**
 * Standard repository for {@link SentenceOccurrence} objects.
 * 
 * @author Pierre Martin
 *
 */
public interface SentenceOccurrenceRepository extends JpaRepository<SentenceOccurrence, SentenceOccurrenceId>{
}
