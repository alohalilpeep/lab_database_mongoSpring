package mongodb.demo.mongodemo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private LocalDate published;

    public Book(String title, String author, LocalDate published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    // Геттеры и сеттеры
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public LocalDate getPublished() { return published; }
    public void setPublished(LocalDate published) { this.published = published; }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", published=" + published +
                '}';
    }
}
