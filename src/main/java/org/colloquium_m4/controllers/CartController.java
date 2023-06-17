package org.colloquium_m4.controllers;

import org.colloquium_m4.domain.dto.CartInfoDto;
import org.colloquium_m4.domain.entities.Book;
import org.colloquium_m4.domain.entities.Cart;
import org.colloquium_m4.domain.entities.CartItem;
import org.colloquium_m4.domain.entities.Response;
import org.colloquium_m4.services.BookService;
import org.colloquium_m4.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    public CartService cartService;
    public BookService bookService;

    public CartController(CartService cartService, BookService bookService) {
        this.cartService = cartService;
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCart(@PathVariable Integer id) {
        Response response = new Response();
        var cart = cartService.findById(id);
        return cart.map(
                value -> new ResponseEntity<>(response.setString("Корзина с ID = " + id).setCart(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(response.setString("Корзины с ID = " + id + " не существует!"),
                        HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Response> addToCart(@RequestBody CartInfoDto cartInfoDto, @PathVariable Integer id) {
        Response response = new Response();

        if (bookService.getBookById(cartInfoDto.getId()).isEmpty()) {
            return new ResponseEntity<>(response.setString("Ошибка! Книга с заданным ID = " + cartInfoDto.getId() + " не найдена!"), HttpStatus.BAD_REQUEST);
        }
        Integer availableAmount = bookService.getBookById(cartInfoDto.getId()).get().getAmount();
        if (availableAmount < cartInfoDto.getAmount()) {
            return new ResponseEntity<>(response.setString("Ошибка! На складе нет столько книг (amount = " + availableAmount + ")"), HttpStatus.BAD_REQUEST);
        }
        if (cartInfoDto.getAmount() <= 0) {
            return new ResponseEntity<>(response.setString("Ошибка! Количество книг не может быть <= 0"), HttpStatus.BAD_REQUEST);
        }
        Book book = bookService.getBookById(cartInfoDto.getId()).get();
        Cart cart = cartService.findById(id)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setId(id);
                    return cartService.saveCart(newCart);
                });

        CartItem cartItem = new CartItem().setBook(book).setCart(cart).setQuantity(cartInfoDto.getAmount());
        cart.getItems().add(cartItem);
        cart.setPrice(cart.getPrice().add(book.getPrice().multiply(BigDecimal.valueOf(cartInfoDto.getAmount()))));
        bookService.updateBookAmount(book.getId(), book.getAmount() - cartInfoDto.getAmount());
        cartService.saveCart(cart);

        return new ResponseEntity<>(response.setString("Книга добавлена в корзину!").setCart(cart), HttpStatus.OK);
    }
}
