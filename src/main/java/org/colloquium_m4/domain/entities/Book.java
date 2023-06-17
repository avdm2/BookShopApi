package org.colloquium_m4.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", length = 300)
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
}
