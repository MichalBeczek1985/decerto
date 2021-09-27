package com.homework.decerto.repository;

import com.homework.decerto.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value  = "SELECT a FROM Author a WHERE a.firstName = ?1 and a.lastName=?2")
    Author findAuthorByName(String firstName, String lastName);
}
