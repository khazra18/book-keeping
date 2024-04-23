package com.bookkeeping.Service;

import com.bookkeeping.DTO.SuccessResponse;
import com.bookkeeping.Entity.Book;
import com.bookkeeping.Repository.BookKeepingRepository;
import com.bookkeeping.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookKeepingRepository bookKeepingRepository;

    @Override
    public Book createBook(Book book) {
        return bookKeepingRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookKeepingRepository.findAll();
    }

    @Override
    public Book getBookById(int id) {
        try {
            return bookKeepingRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found"));
        } catch (BookNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book updateBook(int id, Book book) {
        Book bookFromDB;
        try {
            bookFromDB = bookKeepingRepository.findById(id)
                    .map(book1 -> {
                        book1.setName(book.getName());
                        book1.setDescription(book.getDescription());
                        book1.setAuthor(book.getAuthor());
                        book1.setPrice(book.getPrice());
                        book1.setIsbn(book.getIsbn());
                        book1.setClassification(book.getClassification());
                        return bookKeepingRepository.save(book1);
                    })
                    .orElseThrow(() -> new BookNotFoundException("Book Not Found"));
        } catch (BookNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bookFromDB;
    }

    @Override
    public Book updateBookPartially(int id, Book book) {

        Book bookFromDB = null;
        try {
            bookFromDB = bookKeepingRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found"));
        } catch (BookNotFoundException e) {
            throw new RuntimeException(e);
        }

        Class<?> bookClass = Book.class;
        Field[] bookFields = bookClass.getDeclaredFields();
        for (Field field : bookFields) {
            log.info("Field name : {}", field.getName());
            field.setAccessible(true);
            try {
                if (!field.getName().equalsIgnoreCase("id")) {
                    Object value = field.get(book);
                    if (value != null) {
                        field.set(bookFromDB, value);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        bookKeepingRepository.save(bookFromDB);

        return bookFromDB;
    }

    @Override
    public SuccessResponse deleteBookById(int id) {

        try {
            Book book = bookKeepingRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
            bookKeepingRepository.delete(book);

            return SuccessResponse.builder()
                    .status("Success")
                    .message("Book deleted successfully")
                    .build();
        } catch (BookNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
