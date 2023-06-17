package org.colloquium_m4.domain.dto;

import lombok.Data;
import org.colloquium_m4.domain.entities.Genre;

import java.math.BigDecimal;

@Data
public class BookDto {

    private Integer amount;
    private String author;
    private String title;
    private String description;
    private Genre genre;
    private BigDecimal price;
}
