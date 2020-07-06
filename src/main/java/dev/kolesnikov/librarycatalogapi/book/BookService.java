package dev.kolesnikov.librarycatalogapi.book;

import dev.kolesnikov.librarycatalogapi.review.Review;
import dev.kolesnikov.librarycatalogapi.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository repository;
    @Autowired
    ReviewService reviewService;

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBook(int id) {
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("No book with id="+id+" found"));
    }

    public void takeBook(int id) {
        Book book = repository.findById(id).orElseThrow(()->new EntityNotFoundException("No book with id="+id+" found"));
        if(book.isTaken()) {
            throw new IllegalStateException("Book with id="+id+" is already taken");
        }
        book.setTaken(true);
        repository.save(book);
    }

    public void returnBook(int id) {
        Book book = repository.findById(id).orElseThrow(()->new EntityNotFoundException("No book with id="+id+" found"));
        if(!book.isTaken()) {
            throw new IllegalStateException("Book with id="+id+" is already returned");
        }
        book.setTaken(false);
        repository.save(book);
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
            throw new EntityNotFoundException("No book with id="+id+" found");
        }
    }

    public void deleteBook(int id) {
        if(repository.existsById(id)) {
            List<Review> reviews = reviewService.getAllReviews(id);
            for(Review review : reviews) {
                reviewService.deleteReview(review.getId());
            }
            repository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException("No book with id="+id+" found");
        }
    }
}
