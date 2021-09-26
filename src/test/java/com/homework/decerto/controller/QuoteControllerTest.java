package com.homework.decerto.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.decerto.converter.QuoteConverter;
import com.homework.decerto.dto.QuoteDTO;
import com.homework.decerto.entity.Quote;
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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(QuoteController.class)
class QuoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    QuoteRepository quoteRepository;
    @MockBean
    QuoteConverter quoteConverter;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    void getAllQuotes() {
    }

    @Test
    void saveQuote() throws Exception {
        QuoteDTO quoteDTO = QuoteDTO.builder()
                .firstName("michal")
                .lastName("beczek")
                .quote("lorem ipsum")
                .build();
        QuoteDTO savedquoteDTO = QuoteDTO.builder()
                .id(1L)
                .firstName("michal")
                .lastName("beczek")
                .quote("lorem ipsum")
                .build();
        Quote quote = Quote.builder()
                .id(1L)
                .firstName("michal")
                .lastName("beczek")
                .quote("lorem ipsum")
                .creationDate(new Date())
                .updateDate(new Date())
                .build();
        Mockito.when(quoteConverter.dtoToEntity(quoteDTO)).thenReturn(quote);
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
    void updateQuote() {
    }

    @Test
    void deleteQuote() {
    }
}