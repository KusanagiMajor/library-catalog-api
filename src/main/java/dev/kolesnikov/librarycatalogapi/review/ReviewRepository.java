package dev.kolesnikov.librarycatalogapi.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByBookId(int bookId);
    List<Review> findTop2ByBookIdOrderByIdDesc(int bookId);

    // Query Creation Doc
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
}
