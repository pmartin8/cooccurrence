package com.marteksolution.cooccurrence.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marteksolution.cooccurrence.database.entity.Language;
import com.marteksolution.cooccurrence.database.entity.Url;

/**
 * Standard repository for {@link Url} objects.
 * 
 * @author Pierre Martin
 *
 */
public interface LanguageRepository extends JpaRepository<Language, Short>{
}
