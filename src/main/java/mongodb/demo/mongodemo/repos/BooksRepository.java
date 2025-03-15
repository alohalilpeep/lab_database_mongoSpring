package mongodb.demo.mongodemo.repos;

import mongodb.demo.mongodemo.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends MongoRepository<Book, String> {
    // ключевые слова - https://github.com/spring-projects/spring-data-commons/blob/2.4.5/src/main/java/org/springframework/data/repository/query/parser/PartTree.java#L59
    Optional<Book> getByTitle(String title);
    List<Book> findByTitle(String title);
    List<Book> findByTitleNot(String title);
    List<Book> findByTitleContaining(String string);
    List<Book> findByTitleNotContaining(String title);
    List<Book> findByTitleMatches(String string);
    List<Book> findByPublishedAfter(LocalDate date);
    List<Book> findByPublishedGreaterThanEqual(LocalDate date);
    @Query("{ 'published': { $gte: ?0, $lte: ?1 } }")
    List<Book> findByPublishedBetween(LocalDate starting, LocalDate ending);
    Page<Book> findByPublishedAfter(LocalDate date, Pageable pageable);
    List<Book> findByTitleNullAndPublishedAfter(LocalDate date);
    Slice<Book> findByTitleNullAndPublishedAfter(LocalDate date, Pageable pageable);
    Page<Book> findPageByTitleNullAndPublishedAfter(LocalDate date, Pageable pageable);
    List<Book> findByTitleStartingWith(String string, Sort sort);
    Slice<Book> findByTitleStartingWith(String string, Pageable pageable);
    Page<Book> findPageByTitleStartingWith(String string, Pageable pageable);
}


