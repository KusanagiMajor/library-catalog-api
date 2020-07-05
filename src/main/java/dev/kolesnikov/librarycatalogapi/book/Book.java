package dev.kolesnikov.librarycatalogapi.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.kolesnikov.librarycatalogapi.review.Review;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean taken = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
