package com.marteksolution.cooccurrence.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marteksolution.cooccurrence.database.entity.UnknownWord;

/**
 * Standard repository for {@link UnknownWord} objects.
 * 
 * @author Pierre Martin
 *
 */
public interface UnknownWordRepository extends JpaRepository<UnknownWord, Integer>{
}
