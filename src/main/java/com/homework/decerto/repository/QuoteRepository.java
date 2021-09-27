package com.homework.decerto.repository;

import com.homework.decerto.entity.Author;
import com.homework.decerto.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface QuoteRepository extends JpaRepository<Quote,Long> {
    @Query(value  = "SELECT q FROM Quote q WHERE q.author = ?1 and q.quote=?2")
    Collection<Quote> findMatchingQuote(Author author, String quote);
}
