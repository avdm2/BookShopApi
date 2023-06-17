package org.colloquium_m4.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Response {

    private String string;
    private List<Book> list;
    private Book book;
    private Cart cart;
    private Order order;
}
