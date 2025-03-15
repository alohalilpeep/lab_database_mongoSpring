package mongodb.demo.mongodemo.services.impl;

import mongodb.demo.mongodemo.models.Book;
import mongodb.demo.mongodemo.repos.BooksRepository;
import mongodb.demo.mongodemo.services.BooksService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BooksServiceImpl implements BooksService {
    private final BooksRepository booksRepo;

    public BooksServiceImpl(BooksRepository booksRepo) {
        this.booksRepo = booksRepo;
    }

    @Override
    public Book createBook(Book book) {
        booksRepo.save(book);
        return book;
    }
    @Override
    public Page<Book> getBooks(Pageable pageable) {
        Page<Book> books = booksRepo.findAll(pageable);
        return books;
    }
    @Override
    public List<Book> getAllBooks() {
        return booksRepo.findAll();
    }
    @Override
    public Book getBook(String id) {
        return booksRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
    }
    @Override
    public void deleteBook(String id) {
        booksRepo.deleteById(id);
    }
    @Override
    public Page<Book> findPublishedAfter(LocalDate afterDate, Pageable pageable) {
        Page<Book> books = booksRepo.findByPublishedAfter(afterDate, pageable);
        return books;
    }
    @Override
    public void deleteAllBooks() {
        booksRepo.deleteAll();
    }

    @Override
    public void saveAllBooks(List<Book> books) {
        booksRepo.saveAll(books);
    }
    @Override
    public Page<Book> findBooksMatchingAll(Book probe, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        Page<Book> books = booksRepo.findAll(Example.of(probe, matcher), pageable);
        return books;
    }
    @Override
    public List<Book> findPublishedAfterWithoutPage(LocalDate afterDate) {
        List<Book> books = booksRepo.findByPublishedAfter(afterDate);
        return books;
    }

    @Override
    public List<Book> findByTitleContaining(String title) {
        List<Book> books = booksRepo.findByTitleContaining(title);
        return books;
    }

    @Override
    public List<Book> findByTitleStartingWith(String title, Sort sort) {
        List<Book> books = booksRepo.findByTitleStartingWith(title, sort);
        return books;
    }

    @Override
    public Slice<Book> findByTitleStartingWith(String string, Pageable pageable) {
        Slice<Book> books = booksRepo.findByTitleStartingWith(string, pageable);
        return books;
    }
}

