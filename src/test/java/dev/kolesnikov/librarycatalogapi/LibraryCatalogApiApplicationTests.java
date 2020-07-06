package dev.kolesnikov.librarycatalogapi;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kolesnikov.librarycatalogapi.book.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureRestDocs
class LibraryCatalogApiApplicationTests {

    @Autowired
    protected ObjectMapper objectMapper;
    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .alwaysDo(MockMvcRestDocumentation.document("{method-name}"/*{class-name}/{method-name}"*/,
                        Preprocessors.preprocessRequest(),
                        Preprocessors.preprocessResponse(
                                ResponseModifyingPreprocessors.replaceBinaryContent(),
                                ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                                Preprocessors.prettyPrint())))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(9090)
                        .and().snippets()
                        .withDefaults(CliDocumentation.curlRequest(),
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse(),
                                AutoDocumentation.requestFields(),
                                AutoDocumentation.responseFields(),
                                AutoDocumentation.pathParameters(),
                                AutoDocumentation.requestParameters(),
                                AutoDocumentation.description(),
                                AutoDocumentation.methodAndPath(),
                                AutoDocumentation.section()))
                .build();
    }

    // Tests ordered to generate more beautiful documentation
	// Actually this is more like usage example and not true API testing

    @Test
    @Order(1)
    public void testPostBook() throws Exception {
        List<Map<String, String>> books = new LinkedList<>();

        Map<String, String> book1 = new LinkedHashMap<>();
        book1.put("author", "George Orwell");
        book1.put("name", "1984");
        books.add(book1);

        Map<String, String> book2 = new LinkedHashMap<>();
        book2.put("author", "Aldous Huxley");
        book2.put("name", "Brave New World");
        books.add(book2);

        // Adding Books to Library Catalog
        for (Map<String, String> book : books) {
            String payload = new ObjectMapper().writeValueAsString(book);
            mockMvc.perform(post("/api/v1/books")
                    .content(payload)
                    .contentType("application/json"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    @Order(2)
    public void testUpdateBook() throws Exception {
        Map<String, String> updatedBook = new LinkedHashMap<>();
        updatedBook.put("author", "George Orwell");
        updatedBook.put("name", "1.9.8.4.");

        // Updating Book (id=1) name
        String payload = new ObjectMapper().writeValueAsString(updatedBook);
        mockMvc.perform(put("/api/v1/books/1")
                .content(payload)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void testGetAllBooks() throws Exception {
        // Getting all Books from Library Catalog
        mockMvc.perform(get("/api/v1/books")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void testTakeBook() throws Exception {
        // Marking Book as "Taken"
        mockMvc.perform(get("/api/v1/books/1/take")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void testReturnBook() throws Exception {
        // Marking Book as "Not Taken"
        mockMvc.perform(get("/api/v1/books/1/return")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    public void testPostReview() throws Exception {
        List<Map<String, String>> reviews = new LinkedList<>();

        Map<String, String> review1 = new LinkedHashMap<>();
        review1.put("nickname", "Kusanagi");
        review1.put("rating", "1");
		review1.put("text", "Contains spoilers for real life");
        reviews.add(review1);

        Map<String, String> review2 = new LinkedHashMap<>();
        review2.put("rating", "10");
        review2.put("text", "I like anti-utopia in general, but this one is legendary");
        reviews.add(review2);

		Map<String, String> review3 = new LinkedHashMap<>();
		review3.put("nickname", "Vasya");
		review3.put("rating", "6");
		review3.put("text", "Boring novel, I better read some manga");
		reviews.add(review3);

        // Adding Reviews to Book with id=1
        for (Map<String, String> review : reviews) {
            String payload = new ObjectMapper().writeValueAsString(review);
            mockMvc.perform(post("/api/v1/books/1/reviews")
                    .content(payload)
                    .contentType("application/json"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    @Order(7)
    public void testUpdateReview() throws Exception {
        Map<String, String> review = new LinkedHashMap<>();
        review.put("nickname", "Kusanagi");
        review.put("rating", "7");
        review.put("text", "Contains spoilers for real life, but not bad actually");
        // Updating Review with id=1
        String payload = new ObjectMapper().writeValueAsString(review);
        mockMvc.perform(put("/api/v1/reviews/1")
                .content(payload)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

	@Test
	@Order(8)
	public void testGetAllReviews() throws Exception {
		// Getting all Reviews for Book with id=1
		mockMvc.perform(get("/api/v1/books/1/reviews")
				.contentType("application/json"))
				.andExpect(status().isOk());
	}

    @Test
    @Order(9)
    public void testDeleteReview() throws Exception {
        // Deleting Review with id=1
        mockMvc.perform(delete("/api/v1/reviews/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(10)
    public void testGetBookById() throws Exception {
        // Getting Books by id=1 (additionally shows "Taken" state and reviews)
        mockMvc.perform(get("/api/v1/books/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(11)
    public void testDeleteBook() throws Exception {
        // Deleting Book with id=1
        mockMvc.perform(delete("/api/v1/books/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
