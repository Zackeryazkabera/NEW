package ca.college.zackeryazk.database;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ca.college.zackeryazk.beans.Book;
import ca.college.zackeryazk.beans.Review;

import java.util.List;

@Component
public class DatabaseAccess {

	private final JdbcTemplate jdbcTemplate;

	public DatabaseAccess(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Method to retrieve all books from the database
	public List<Book> getAllBooks() {
		String query = "SELECT * FROM books";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Book.class));
	}

	// Method to retrieve reviews for a specific book
	public List<Review> getReviewsByBookId(Long bookId) {
		String query = "SELECT * FROM reviews WHERE bookId = ?";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Review.class), bookId);
	}

	// Method to add a new book to the database
	public void addBook(Book book) {
		String query = "INSERT INTO books (title, author) VALUES (?, ?)";
		jdbcTemplate.update(query, book.getTitle(), book.getAuthor());
	}

	// Method to add a new review for a book to the database
	public void addReview(Review review) {
		String query = "INSERT INTO reviews (bookId, text) VALUES (?, ?)";
		jdbcTemplate.update(query, review.getBookId(), review.getText());
	}

}
