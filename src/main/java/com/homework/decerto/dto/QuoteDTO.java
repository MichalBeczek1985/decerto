package com.homework.decerto.dto;

import com.homework.decerto.entity.Author;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class QuoteDTO {
    private Long id;
    private Author author;
    private String quote;
}
