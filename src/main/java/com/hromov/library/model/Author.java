package com.hromov.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    @Column(name = "name")
    private String name;
}
