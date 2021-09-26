package com.homework.decerto.controller;

import com.homework.decerto.converter.QuoteConverter;
import com.homework.decerto.dto.QuoteDTO;
import com.homework.decerto.entity.Quote;
import com.homework.decerto.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class QuoteController {

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
            Collection<Quote> matchingQuote = quoteRepository.findMatchingQuote(quoteDTO.getFirstName().trim(),
                    quoteDTO.getLastName().trim(), quoteDTO.getQuote().trim());
            if (!matchingQuote.isEmpty()){
                return new ResponseEntity<>("Record already exists", HttpStatus.FOUND);
            }
            else{
                Quote quote = quoteConverter.dtoToEntity(quoteDTO);
                Quote save = quoteRepository.save(quote);
                return new ResponseEntity<>(quoteConverter.entityToDTO(save),HttpStatus.CREATED);
            }
    }

    @PutMapping("/quote")
    public ResponseEntity<Object> updateQuote(@RequestBody QuoteDTO quoteDTO){
        Optional<Quote> byId = quoteRepository.findById(quoteDTO.getId());
        if (byId.isPresent()){
            quoteRepository.save(quoteConverter.dtoToEntity(quoteDTO));
            return new ResponseEntity<>(quoteDTO,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Record doesn't exist", HttpStatus.NOT_FOUND);
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
