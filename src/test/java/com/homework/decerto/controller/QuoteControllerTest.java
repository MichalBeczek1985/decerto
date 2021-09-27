package com.homework.decerto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.decerto.converter.QuoteConverter;
import com.homework.decerto.dto.QuoteDTO;
import com.homework.decerto.entity.Author;
import com.homework.decerto.entity.Quote;
import com.homework.decerto.repository.AuthorRepository;
import com.homework.decerto.repository.QuoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(QuoteController.class)
class QuoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    QuoteRepository quoteRepository;
    @MockBean
    AuthorRepository authorRepository;
    @MockBean
    QuoteConverter quoteConverter;
    @Autowired
    ObjectMapper objectMapper;
    private Author author;
    private Author savedAuthor;
    private QuoteDTO quoteDTO;
    private QuoteDTO savedquoteDTO;
    private Quote quote;
    private Quote updatedQuote;
    private QuoteDTO updatedDTO;

    @BeforeEach
    void setUp(){
        author = new Author(null,"michal","beczek");
        savedAuthor = new Author(1L,"michal","beczek");
        quoteDTO = QuoteDTO.builder()
                .author(author)
                .quote("lorem ipsum")
                .build();
        savedquoteDTO = QuoteDTO.builder()
                .id(1L)
                .author(savedAuthor)
                .quote("lorem ipsum")
                .build();
        quote = Quote.builder()
                .id(1L)
                .author(savedAuthor)
                .quote("lorem ipsum")
                .creationDate(new Date())
                .updateDate(new Date())
                .build();
        updatedDTO = QuoteDTO.builder()
                .id(1L)
                .quote("lorem ipsumus")
                .build();
        updatedQuote = Quote.builder()
                .id(1L)
                .author(savedAuthor)
                .quote("lorem ipsumus")
                .creationDate(null)
                .updateDate(null)
                .build();
    }


    @Test
    void getAllQuotes() throws Exception {
        List<Quote> all = new ArrayList<>();
        Quote quote = Quote.builder()
                .id(1L)
                .author(new Author(1L, "janek", "kowalski"))
                .quote("lorem ipsum")
                .build();
        Quote quote2 = Quote.builder()
                .id(2L)
                .author(new Author(2L, "marek", "nowak"))
                .quote("lorem ipsum2")
                .build();
        Quote quote3 = Quote.builder()
                .id(3L)
                .author(new Author(3L, "tadzik", "szczepanik"))
                .quote("lorem ipsum3")
                .build();
    all.add(quote);
    all.add(quote2);
    all.add(quote3);
        List<QuoteDTO> quoteDTOS = quoteConverter.entitiesToDTOs(all);
        Mockito.when(quoteRepository.findAll()).thenReturn(all);
        Mockito.when(quoteConverter.entitiesToDTOs(all)).thenReturn(quoteDTOS);

        String url = "/quote";

        mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .contentType("application/json")
                       .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(quoteDTOS)));


    }

    @Test
    void saveQuote() throws Exception {

        Mockito.when(authorRepository.findAuthorByName(author.getFirstName(), author.getLastName())).thenReturn(savedAuthor);
        Mockito.when(quoteRepository.findMatchingQuote(author, quote.getQuote())).thenReturn(new HashSet<>());
        Mockito.when(quoteConverter.dtoToEntity(Mockito.any())).thenReturn(quote);
        Mockito.when(quoteRepository.save(quote)).thenReturn(quote);
        Mockito.when(quoteConverter.entityToDTO(quote)).thenReturn(savedquoteDTO);

        String url = "/quote";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quoteDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(savedquoteDTO)));
    }

    @Test
    void saveQuoteWhenAuthorDoesntExist() throws Exception {
        Mockito.when(authorRepository.findAuthorByName(Mockito.any(),Mockito.any())).thenReturn(null);
        Mockito.when(quoteConverter.dtoToEntity(Mockito.any())).thenReturn(quote);
        Mockito.when(quoteRepository.save(quote)).thenReturn(quote);
        Mockito.when(quoteConverter.entityToDTO(quote)).thenReturn(savedquoteDTO);

        String url = "/quote";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quoteDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(savedquoteDTO)));
    }

    @Test
    void updateQuote() throws Exception {
        Mockito.when(quoteRepository.findById(updatedDTO.getId()))
                .thenReturn(Optional.of(quote));
        Mockito.when(quoteRepository.save(Mockito.any())).thenReturn(updatedQuote);
        Mockito.when(quoteConverter.entityToDTO(updatedQuote)).thenReturn(updatedDTO);

        QuoteDTO quoteDTO = quoteConverter.entityToDTO(updatedQuote);
        String url = "/quote";

        mockMvc.perform(MockMvcRequestBuilders
                        .put(url)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(quoteDTO)));
    }
    @Test
    void updateQuoteWhenQuoteNotFound() throws Exception {
        Mockito.when(quoteRepository.findById(updatedDTO.getId()))
                .thenReturn(Optional.empty());
        String url = "/quote";

        mockMvc.perform(MockMvcRequestBuilders
                        .put(url)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Resource not found"));
    }
    @Test
    void deleteQuote() throws Exception {
        Mockito.when(quoteRepository.findById(1L)).thenReturn(Optional.ofNullable(quote));
        String url = "/quote/"+quote.getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(url))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully deleted"));
    }

    @Test
    void deleteQuoteWhenQuoteNotFound() throws Exception {
        Mockito.when(quoteRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        String url = "/quote/"+2;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(url))
                .andExpect(status().isNotFound());
    }
}