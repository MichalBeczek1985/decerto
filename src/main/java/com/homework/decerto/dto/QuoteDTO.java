package com.homework.decerto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuoteDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String quote;
}
