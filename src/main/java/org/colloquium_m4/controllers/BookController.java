package org.colloquium_m4.controllers;

import org.colloquium_m4.domain.dto.BookDto;
import org.colloquium_m4.domain.entities.Book;
import org.colloquium_m4.domain.entities.Response;
import org.colloquium_m4.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    public BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Response> getAllBooks() {
        List<Book> list = bookService.getAllBooks();
        Response response = new Response()
                .setString(list.isEmpty() ? "Список книг пуст" : "Список книг")
                .setList(list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBookById(@PathVariable Integer id) {
        Response response = new Response();
        Optional<Book> book = bookService.getBookById(id);
        if (book.isEmpty()) {
            return new ResponseEntity<>(response.setString("ID указан некорректно!"),
                    HttpStatus.BAD_REQUEST);
        }
        response.setBook(book.get()).setString("Книга с ID = " + id);
        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @PostMapping("/create")
    public ResponseEntity<Response> createBook(@RequestBody BookDto bookDto) {
        Response response = new Response();
        if (bookDto.getAmount() == null || bookDto.getAuthor() == null || bookDto.getDescription() == null ||
                bookDto.getTitle() == null || bookDto.getGenre() == null || bookDto.getPrice() == null) {
            return new ResponseEntity<>(response.setString("Ошибка! Не все поля заполнены!"),
                    HttpStatus.BAD_REQUEST);
        }
        Book book = new Book()
                .setPrice(bookDto.getPrice())
                .setGenre(bookDto.getGenre())
                .setAmount(bookDto.getAmount())
                .setAuthor(bookDto.getAuthor())
                .setTitle(bookDto.getTitle())
                .setDescription(bookDto.getDescription());
        bookService.saveBook(book);
        return new ResponseEntity<>(response.setBook(book).setString("Книга добавлена!"), HttpStatus.OK);
    }
}
