package com.marteksolution.cooccurrence.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marteksolution.cooccurrence.database.entity.WordVariation;

/**
 * Standard repository for {@link WordVariation} objects.
 * 
 * @author Pierre Martin
 *
 */
public interface WordVariationRepository extends JpaRepository<WordVariation, Integer>{
}
