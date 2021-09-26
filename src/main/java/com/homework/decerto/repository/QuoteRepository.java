package com.homework.decerto.repository;

import com.homework.decerto.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface QuoteRepository extends JpaRepository<Quote,Long> {
    @Query(value  = "SELECT q FROM Quote q WHERE q.firstName = ?1 and q.lastName=?2 and q.quote=?3")
    Collection<Quote> findMatchingQuote(String name,String lastname, String quote);
}
