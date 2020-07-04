package dev.kolesnikov.librarycatalogapi.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable int id) {
        return bookService.getBook(id);
    }

    @PostMapping("/books")
    public void addBook(@Valid @RequestBody Book book) {
        bookService.addBook(book);
    }

    @PutMapping("/books/{id}")
    public void updateBook(@PathVariable int id, @Valid @RequestBody Book book) {
        bookService.updateBook(id, book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }
}
