package dev.kolesnikov.librarycatalogapi.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.kolesnikov.librarycatalogapi.book.Book;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_generator")
    @SequenceGenerator(name = "review_id_generator", sequenceName = "review_id_seq")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @Size(max = 24, message = "Nickname is too long")
    private String nickname = "Anonymous";

    @Min(value = 1, message = "Rating must be 1-10")
    @Max(value = 10, message = "Rating must be 1-10")
    @NotNull(message = "Rating is mandatory")
    private int rating;

    @Size(max = 10485760, message = "Text is too long")
    @NotBlank(message = "Text is mandatory")
    private String text;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}