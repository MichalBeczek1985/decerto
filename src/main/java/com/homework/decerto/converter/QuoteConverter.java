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
        return QuoteDTO.builder()
                .id(quote.getId())
                .author(quote.getAuthor())
                .quote(quote.getQuote())
                .build();
    }

    public List<QuoteDTO> entitiesToDTOs(List<Quote> quotes){
        return quotes.stream().map(x->entityToDTO(x)).collect(Collectors.toList());
    }

    public Quote dtoToEntity(QuoteDTO quoteDTO){
        return Quote.builder()
                .id(quoteDTO.getId())
                .author(quoteDTO.getAuthor())
                .quote(quoteDTO.getQuote())
                .creationDate(new Date())
                .updateDate(new Date())
                .build();
    }

}
