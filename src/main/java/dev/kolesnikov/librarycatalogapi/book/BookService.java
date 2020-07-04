package dev.kolesnikov.librarycatalogapi.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public List<Book> getAllBooks() {
        return new ArrayList<>(repository.findAll());
    }

    public void addBook(Book book) {
        repository.save(book);
    }
}
