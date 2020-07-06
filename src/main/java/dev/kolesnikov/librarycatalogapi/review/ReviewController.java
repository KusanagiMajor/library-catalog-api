package dev.kolesnikov.librarycatalogapi.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/books/{bookId}/reviews")
    public List<Review> getAllReviews(@PathVariable int bookId) {
        return reviewService.getAllReviews(bookId);
    }

    @PostMapping("/books/{bookId}/reviews")
    public void addReview(@PathVariable int bookId, @Valid @RequestBody Review review) {
        reviewService.addReview(bookId, review);
    }

    @PutMapping("/reviews/{id}")
    public void updateReview(@PathVariable int id, @Valid @RequestBody Review review) {
        reviewService.updateReview(id, review);
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}