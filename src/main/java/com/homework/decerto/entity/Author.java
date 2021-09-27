package com.homework.decerto.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(uniqueConstraints=
@UniqueConstraint(columnNames={"firstName", "lastName"}))
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
}
