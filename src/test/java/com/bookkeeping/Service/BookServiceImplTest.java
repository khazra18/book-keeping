package com.bookkeeping.Service;

import com.bookkeeping.DTO.SuccessResponse;
import com.bookkeeping.Entity.Book;
import com.bookkeeping.Entity.Classification;
import com.bookkeeping.Repository.BookKeepingRepository;
import com.bookkeeping.exception.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceImplTest {

    @Mock
    private BookKeepingRepository bookKeepingRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setupBookEntity() {
        book = Book.builder()
                .id(1)
                .name("Half girlfriend")
                .price(100.0)
                .author("Chetan bhagat")
                .isbn("ABC-1234")
                .description("About my girlfriend")
                .classification(Classification.MYSTERY)
                .build();
    }


    @Test
    @DisplayName("Test case for create book")
    public void createBookTestCase() {

        when(bookKeepingRepository.save(any())).thenReturn(book);
        Book testBook = bookService.createBook(book);
        assertEquals(book.getId(), testBook.getId());

    }

    @Test
    @DisplayName("Test case for get all books")
    public void getAllBooksTestCase() {

        List<Book> getAllBooksList = new ArrayList<>();
        getAllBooksList.add(book);

        when(bookKeepingRepository.findAll()).thenReturn(getAllBooksList);
        List<Book> allBooksTestList = bookService.getAllBooks();

        assertEquals(allBooksTestList.size(), getAllBooksList.size());

    }


    @Test
    @DisplayName("Test case for getbookbyid")
    public void getBookByIdTestCase() {

        when(bookKeepingRepository.findById(anyInt())).thenReturn(Optional.ofNullable(book));
        Book bookById = bookService.getBookById(anyInt());

        assertEquals(bookById.getId(), book.getId());

    }

    @Test
    @DisplayName("Test case for update book")
    public void updateBookTestCase() {

        int bookId = 1;

        Book updatedBook = getUpdateBook(bookId);

        when(bookKeepingRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookKeepingRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Act
        Book result = bookService.updateBookPartially(bookId, updatedBook);

        // Assert
        assertEquals(updatedBook, result);
        assertEquals(updatedBook.getName(), result.getName());
        // assertEquals(updatedBook.getDescription(), result.getDescription());
        //assertEquals(updatedBook.getAuthor(), result.getAuthor());
        //assertEquals(updatedBook.getPrice(), result.getPrice());
        assertEquals(updatedBook.getIsbn(), result.getIsbn());
        assertEquals(updatedBook.getClassification(), result.getClassification());

        verify(bookKeepingRepository, times(1)).findById(bookId);
        verify(bookKeepingRepository, times(1)).save(any(Book.class));


    }

    @Test
    @DisplayName("Test case for update book")
    public void updateBookPartiallyTestCase() {

        int bookId = 1;

        Book updatedBook = getUpdateBook(bookId);

        when(bookKeepingRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookKeepingRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(bookId, updatedBook);

        assertEquals(updatedBook.getIsbn(), result.getIsbn());
        assertEquals(updatedBook.getClassification(), result.getClassification());

        verify(bookKeepingRepository, times(1)).findById(bookId);
        verify(bookKeepingRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Delete book method success test case")
    public void testDeleteBookById_Success() {

        int bookId = 1;
        when(bookKeepingRepository.findById(bookId)).thenReturn(Optional.of(book));

        SuccessResponse successResponse = bookService.deleteBookById(bookId);

        assertEquals("Success", successResponse.getStatus());
        verify(bookKeepingRepository, times(1)).findById(bookId);
        verify(bookKeepingRepository, times(1)).delete(book);

    }

    @Test
    @DisplayName("Get book by id failure case")
    public void testGetBookById_Failure() {
        int bookId = 1;
        when(bookKeepingRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> bookService.getBookById(bookId));

        //assertEquals(BookNotFoundException.class,runtimeException.getCause().getClass());
        verify(bookKeepingRepository, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Update book failure case")
    public void testUpdateBook_Failure() {
        int bookId = 1;
        when(bookKeepingRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> bookService.updateBook(bookId,book));

        //assertEquals(BookNotFoundException.class,runtimeException.getCause().getClass());
        verify(bookKeepingRepository, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Update book partially failure case")
    public void testUpdateBookPartially_Failure() {
        int bookId = 1;
        when(bookKeepingRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> bookService.updateBookPartially(bookId,book));

        //assertEquals(BookNotFoundException.class,runtimeException.getCause().getClass());
        verify(bookKeepingRepository, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Delete book failure case")
    public void testDeleteBook_Failure() {
        int bookId = 1;
        when(bookKeepingRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> bookService.deleteBookById(bookId));

        //assertEquals(BookNotFoundException.class,runtimeException.getCause().getClass());
        verify(bookKeepingRepository, times(1)).findById(bookId);
    }

    Book getUpdateBook(int bookId) {
        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setName("New Name");
        updatedBook.setDescription("New Description");
        updatedBook.setAuthor("New Author");
        updatedBook.setPrice(25.0);
        updatedBook.setIsbn("ABC-1234");
        updatedBook.setClassification(Classification.FICTION);

        return updatedBook;
    }

}