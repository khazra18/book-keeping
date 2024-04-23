package com.bookkeeping.Controller;

import com.bookkeeping.DTO.SuccessResponse;
import com.bookkeeping.Entity.Book;
import com.bookkeeping.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bookstore/1.0.0")
public class BookKeepingController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/books", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.OK);
    }

    @GetMapping(value = "/books", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping(value = "/books/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/books/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> updateBook(@PathVariable("id") int id, @Valid @RequestBody Book book) {
        return new ResponseEntity<>(bookService.updateBook(id, book), HttpStatus.OK);
    }

    @PatchMapping(value = "/books/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> updateBookPartially(@PathVariable("id") int id,@Valid @RequestBody Book book) {
        return new ResponseEntity<>(bookService.updateBookPartially(id, book), HttpStatus.OK);
    }

    @DeleteMapping(value = "/books/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SuccessResponse> deleteBookById(@PathVariable("id") int id) {
        return new ResponseEntity<>(bookService.deleteBookById(id), HttpStatus.OK);
    }

}
