package com.homework.decerto.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(targetEntity=Author.class, cascade = CascadeType.ALL)
    private Author author;
    private String quote;
    @Column(updatable = false)
    private Date creationDate;
    private Date updateDate;
}
