package com.homework.decerto.controller;

import com.homework.decerto.converter.QuoteConverter;
import com.homework.decerto.dto.QuoteDTO;
import com.homework.decerto.entity.Author;
import com.homework.decerto.entity.Quote;
import com.homework.decerto.repository.AuthorRepository;
import com.homework.decerto.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class QuoteController {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    QuoteRepository quoteRepository;
    @Autowired
    QuoteConverter quoteConverter;
    @GetMapping("/quote")
    public ResponseEntity<Object> getAllQuotes(){
        List<Quote> all = quoteRepository.findAll();
        return new ResponseEntity<>(quoteConverter.entitiesToDTOs(all), HttpStatus.OK);
    }
    @PostMapping("/quote")
    public ResponseEntity<Object> saveQuote(@RequestBody QuoteDTO quoteDTO){
        Author authorByName = authorRepository.findAuthorByName(quoteDTO.getAuthor().getFirstName(), quoteDTO.getAuthor().getLastName());
        if (authorByName!=null) {
            Collection<Quote> matchingQuote = quoteRepository.findMatchingQuote(authorByName, quoteDTO.getQuote().trim());
            if (!matchingQuote.isEmpty()) {
                return new ResponseEntity<>("Record already exists", HttpStatus.FOUND);
            } else {
                return getObjectResponseEntity(quoteDTO);
            }}
        return getObjectResponseEntity(quoteDTO);
    }
    private ResponseEntity<Object> getObjectResponseEntity(@RequestBody QuoteDTO quoteDTO) {
        Quote quote = quoteConverter.dtoToEntity(quoteDTO);
        Quote save = quoteRepository.save(quote);
        return new ResponseEntity<>(quoteConverter.entityToDTO(save), HttpStatus.CREATED);
    }
    @PutMapping("/quote")
    public ResponseEntity<Object> updateQuote(@RequestBody QuoteDTO quoteDTO){
            Optional<Quote> matchingQuote = quoteRepository.findById(quoteDTO.getId());
            if (!matchingQuote.isEmpty()){
                quoteDTO.setAuthor(matchingQuote.get().getAuthor());
                Quote save = quoteRepository.save(quoteConverter.dtoToEntity(quoteDTO));
                return new ResponseEntity<>(quoteConverter.entityToDTO(save),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Resource not found",HttpStatus.NOT_FOUND);
            }
        }
    @DeleteMapping("/quote/{id}")
    public ResponseEntity<Object> deleteQuote(@PathVariable Long id){
        Optional<Quote> byId = quoteRepository.findById(id);
        if(byId.isPresent())
        {quoteRepository.deleteById(byId.get().getId());
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Object not found", HttpStatus.NOT_FOUND);
    }
}
