package mongodb.demo.mongodemo.services;

import mongodb.demo.mongodemo.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public interface BooksService {
    Book createBook(Book book);
    Book getBook(String id);
    void deleteBook(String id);
    void deleteAllBooks();
    void saveAllBooks(List<Book> books);
    Page<Book> getBooks(Pageable pageable);
    List<Book> getAllBooks();
    Page<Book> findPublishedAfter(LocalDate exclusive, Pageable pageable);
    Page<Book> findBooksMatchingAll(Book probe, Pageable pageable);
    List<Book> findByTitleContaining(String title);
    List<Book> findByTitleStartingWith(String title, Sort sort);
    Slice<Book> findByTitleStartingWith(String string, Pageable pageable);

    List<Book> findPublishedAfterWithoutPage(LocalDate afterDate);
}
