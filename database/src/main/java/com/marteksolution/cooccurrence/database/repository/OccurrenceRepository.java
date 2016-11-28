package com.marteksolution.cooccurrence.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marteksolution.cooccurrence.database.entity.Occurrence;

/**
 * Standard repository for {@link Occurrence} objects.
 * 
 * @author Pierre Martin
 *
 */
public interface OccurrenceRepository extends JpaRepository<Occurrence, Integer>{
}
