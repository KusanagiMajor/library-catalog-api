package dev.kolesnikov.librarycatalogapi.book;

import dev.kolesnikov.librarycatalogapi.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getBook(@PathVariable int id) {
        Map<String, Object> body = new LinkedHashMap<>();
        Book book = bookService.getBook(id);
        body.put("book", book);
        body.put("taken", book.isTaken());
        body.put("reviews", reviewService.getTwoLastReviews(id));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/books/{id}/take")
    public void takeBook(@PathVariable int id) {
        bookService.takeBook(id);
    }

    @GetMapping("/books/{id}/return")
    public void returnBook(@PathVariable int id) {
        bookService.returnBook(id);
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
