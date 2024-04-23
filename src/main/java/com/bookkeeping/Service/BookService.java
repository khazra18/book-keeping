package com.bookkeeping.Service;

import com.bookkeeping.DTO.SuccessResponse;
import com.bookkeeping.Entity.Book;

import java.util.List;

public interface BookService {

    Book createBook(Book book);

    List<Book> getAllBooks();

    Book getBookById(int id);

    Book updateBook(int id,Book book);

    Book updateBookPartially(int id,Book book);

    SuccessResponse deleteBookById(int id);
}
