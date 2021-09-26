package com.homework.decerto.converter;

import com.homework.decerto.dto.QuoteDTO;
import com.homework.decerto.entity.Quote;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuoteConverter {
    public QuoteDTO entityToDTO(Quote quote){
        QuoteDTO quoteDTO = QuoteDTO.builder()
                .id(quote.getId())
                .firstName(quote.getFirstName())
                .lastName(quote.getLastName())
                .quote(quote.getQuote())
                .build();
        return  quoteDTO;
    }

    public List<QuoteDTO> entitiesToDTOs(List<Quote> quotes){
        return quotes.stream().map(x->entityToDTO(x)).collect(Collectors.toList());
    }

    public Quote dtoToEntity(QuoteDTO quoteDTO){
        Quote quote = Quote.builder()
                .id(quoteDTO.getId())
                .firstName(quoteDTO.getFirstName())
                .lastName(quoteDTO.getLastName())
                .quote(quoteDTO.getQuote())
                .creationDate(new Date())
                .updateDate(new Date())
                .build();
        return quote;
    }

    public List<Quote> dtosToEntities(List<QuoteDTO> quotes){
        return quotes.stream().map(x->dtoToEntity(x)).collect(Collectors.toList());
    }
}
