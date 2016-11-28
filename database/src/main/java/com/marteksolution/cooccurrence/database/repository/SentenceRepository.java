package com.marteksolution.cooccurrence.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marteksolution.cooccurrence.database.entity.Sentence;

/**
 * Standard repository for {@link Sentence} objects.
 * 
 * @author Pierre Martin
 *
 */
public interface SentenceRepository extends JpaRepository<Sentence, Integer>{
}
