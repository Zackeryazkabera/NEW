package ca.college.zackeryazk.controllers;

import ca.college.zackeryazk.beans.Book;
import ca.college.zackeryazk.beans.Review;
import ca.college.zackeryazk.database.DatabaseAccess;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

	private final DatabaseAccess databaseAccess;

	public BookController(DatabaseAccess databaseAccess) {
		this.databaseAccess = databaseAccess;
	}

	@GetMapping("/{bookId}")
	public String getBookDetails(@PathVariable Long bookId, Model model) {
		Book book = getBookById(bookId);
		if (book != null) {
			List<Review> reviews = databaseAccess.getReviewsByBookId(bookId);
			model.addAttribute("book", book);
			model.addAttribute("reviews", reviews);
			return "view-book";
		} else {
			return "redirect:/";
		}
	}

	@GetMapping("/add")
	public String showAddBookForm() {
		return "admin/add-book";
	}

	@PostMapping("/add")
	public String addBook(@ModelAttribute Book book) {
		databaseAccess.addBook(book);
		return "redirect:/";
	}

	@GetMapping("/{bookId}/add-review")
	public String showAddReviewForm(@PathVariable Long bookId, Model model, Authentication authentication) {
		Book book = getBookById(bookId);
		if (book != null && authentication.isAuthenticated()) {
			model.addAttribute("book", book);
			return "user/add-review";
		} else {
			return "redirect:/";
		}
	}

	@PostMapping("/{bookId}/add-review")
	public String addReview(@PathVariable Long bookId, @ModelAttribute Review review) {
		review.setBookId(bookId);
		databaseAccess.addReview(review);
		return "redirect:/books/" + bookId;
	}

	// Helper method to get book by ID
	private Book getBookById(Long bookId) {
		List<Book> books = databaseAccess.getAllBooks();
		return books.stream().filter(book -> book.getId().equals(bookId)).findFirst().orElse(null);
	}
}
