package dev.kolesnikov.librarycatalogapi.review;

import dev.kolesnikov.librarycatalogapi.book.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ReviewService {
    Logger LOG = LoggerFactory.getLogger(ReviewService.class);
    @Autowired
    ReviewRepository repository;
    @Autowired
    BookService bookService;

    public List<Review> getAllReviews(int bookId) {
        return repository.findByBookId(bookId);
    }

    public List<Review> getTwoLastReviews(int bookId) {
        return repository.findTop2ByBookIdOrderByIdDesc(bookId);
    }

    public void addReview(int bookId, Review review) {
        review.setBook(bookService.getBook(bookId));
        repository.save(review);
        LOG.info("New Review for Book (id="+bookId+") successfully added");
    }

    public void updateReview(int id, Review updatedReview) {
        Review review = repository.findById(id).orElseThrow(()->new EntityNotFoundException("No review with id="+id+" found"));
        updatedReview.setId(id);
        updatedReview.setBook(review.getBook());
        repository.save(updatedReview);
        LOG.info("Review (id="+id+") successfully updated");
    }

    public void deleteReview(int id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException("No review with id="+id+" found");
        }
        LOG.info("Review (id="+id+") successfully deleted");
    }
}