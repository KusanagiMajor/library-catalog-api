package dev.kolesnikov.librarycatalogapi.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {
    @Autowired
    BookRepository repository;

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBook(int id) {
        return repository.findById(id).orElseThrow(()->new NoSuchElementException("No book with id="+id+" found"));
    }

    public void addBook(Book book) {
        repository.save(book);
    }

    public void updateBook(int id, Book book) {
        if(repository.existsById(id)) {
            book.setId(id);
            repository.save(book);
        }
        else {
            throw new NoSuchElementException("No book with id="+id+" found");
        }
    }

    public void deleteBook(int id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
        else {
            throw new NoSuchElementException("No book with id="+id+" found");
        }
    }
}
