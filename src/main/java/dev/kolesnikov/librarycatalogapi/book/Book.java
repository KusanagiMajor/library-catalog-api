package dev.kolesnikov.librarycatalogapi.book;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private Boolean isTaken;

    @PrePersist
    void preInsert() { // Just showing off, I know it can be done with primitive that's "false" by default
        if (this.isTaken == null)
            this.isTaken = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Boolean getTaken() {
        return isTaken;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }
}
