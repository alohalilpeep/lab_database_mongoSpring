package mongodb.demo.mongodemo;

import mongodb.demo.mongodemo.models.Book;
import mongodb.demo.mongodemo.repos.BooksRepository;
import mongodb.demo.mongodemo.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {BooksRepository.class}, repositoryImplementationPostfix = "Impl")
public class MongodemoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MongodemoApplication.class);
        app.setDefaultProperties(Collections.singletonMap("spring.profiles.default", "mongodb"));
        app.run(args);
    }

    @Component
    public class Init implements CommandLineRunner {
        @Autowired
        private BooksService booksService;

        @Override
        public void run(String... args) throws Exception {
            List<Book> existingBooks = booksService.getAllBooks();
            if (existingBooks.isEmpty()) {
                System.out.println("База данных пуста. Добавляем начальные данные...");

                List<Book> initialBooks = Arrays.asList(
                        new Book("Евгений Онегин", "Александр Пушкин", LocalDate.of(1833, 1, 1)),
                        new Book("Мёртвые души", "Николай Гоголь", LocalDate.of(1842, 1, 1)),
                        new Book("Война и мир", "Лев Толстой", LocalDate.of(1869, 1, 1)),
                        new Book("Преступление и наказание", "Федор Достоевский", LocalDate.of(1866, 1, 1)),
                        new Book("Отцы и дети", "Иван Тургенев", LocalDate.of(1862, 1, 1))
                );

                booksService.saveAllBooks(initialBooks);
                System.out.println("Начальные данные добавлены успешно");
            } else {
                System.out.println("База данных уже содержит книги. Пропускаем инициализацию.");
            }
        }
    }

    @Component
    public class CustomCode implements CommandLineRunner {
        @Autowired
        private BooksService booksService;

        @Override
        public void run(String... args) throws Exception {
            List<Book> storedBooks = booksService.getAllBooks();
            for (Book b:storedBooks) {
                System.out.println(b);
            }

            // Задание 1: Добавьте в базу данных новую книгу "Вишневый сад" автора "Антон Чехов" с датой публикации 17 января 1904 года.
            Book cherryOrchard = new Book("Вишневый сад", "Антон Чехов", LocalDate.of(1904, 1, 17));
            booksService.createBook(cherryOrchard);
            System.out.println("Книга 'Вишневый сад' добавлена.");

            // Задание 2: Добавьте сразу три книги А.С. Пушкина: "Капитанская дочка" (1836), "Дубровский" (1841) и "Пиковая дама" (1834).
            List<Book> pushkinBooks = Arrays.asList(
                    new Book("Капитанская дочка", "Александр Пушкин", LocalDate.of(1836, 1, 1)),
                    new Book("Дубровский", "Александр Пушкин", LocalDate.of(1841, 1, 1)),
                    new Book("Пиковая дама", "Александр Пушкин", LocalDate.of(1834, 1, 1))
            );
            booksService.saveAllBooks(pushkinBooks);
            System.out.println("Книги Пушкина добавлены.");

            // Получите первые 3 книги из базы данных, используя пагинацию.
            Pageable pages = PageRequest.of(0, 3);
            Page<Book> page = booksService.getBooks(pages);
            System.out.println("Первые 3 книги:");
            page.getContent().forEach(book -> System.out.println(book));

            // Задание 3: Получите все книги, дата публикации которых позже 1860 года.
            List<Book> booksAfter1860 = booksService.findPublishedAfterWithoutPage(LocalDate.of(1860, 1, 1));
            System.out.println("Книги, опубликованные после 1860 года:");
            booksAfter1860.forEach(book -> System.out.println(book));

            // Задание 4: Найдите все книги, опубликованные после 1860 года, используя пагинацию по 2 книги на странице.
            Pageable pageable = PageRequest.of(0, 2);
            Page<Book> booksPage = booksService.findPublishedAfter(LocalDate.of(1860, 1, 1), pageable);
            System.out.println("Книги, опубликованные после 1860 года (по 2 на странице):");
            booksPage.getContent().forEach(book -> System.out.println(book));

            // Задание 5: Найдите книгу по её ID и выведите информацию о ней
            String bookId = storedBooks.get(0).getId(); // Пример ID первой книги
            Book bookById = booksService.getBook(bookId);
            System.out.println("Книга по ID " + bookId + ": " + bookById);

            // Задание 6: Удалите книгу по её ID
            booksService.deleteBook(bookId);
            System.out.println("Книга с ID " + bookId + " удалена.");

            // Задание 7: Найдите все книги, название которых содержит слово "война"
            List<Book> booksWithWarInTitle = booksService.findByTitleContaining("война");
            System.out.println("Книги, содержащие слово 'война' в названии:");
            booksWithWarInTitle.forEach(book -> System.out.println(book));

            // Задание 8: Получите вторую страницу книг (размер страницы - 5) и выведите даты их публикации.
            Pageable secondPage = PageRequest.of(1, 5);
            Page<Book> secondPageBooks = booksService.getBooks(secondPage);
            System.out.println("Даты публикации книг на второй странице:");
            secondPageBooks.getContent().forEach(book -> System.out.println(book.getPublished()));

            // Задание 9: Найдите все книги, название которых начинается с "В" и отсортируйте их по дате публикации по убыванию.
            Sort sort = Sort.by(Sort.Direction.DESC, "published");
            List<Book> booksStartingWithV = booksService.findByTitleStartingWith("В", sort);
            System.out.println("Книги, название которых начинается с 'В', отсортированные по дате публикации по убыванию:");
            booksStartingWithV.forEach(book -> System.out.println(book));

            // Задание 10: Найдите все книги, название которых начинается с "В" и выведите их постранично (размер страницы - 3).
            Pageable pageableV = PageRequest.of(0, 3);
            Slice<Book> booksStartingWithVPage = booksService.findByTitleStartingWith("В", pageableV);
            System.out.println("Книги, название которых начинается с 'В' (постранично):");
            booksStartingWithVPage.getContent().forEach(book -> System.out.println(book));
        }
    }
}
