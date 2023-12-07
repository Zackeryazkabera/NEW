package ca.college.zackeryazk.controllers;

import ca.college.zackeryazk.beans.Book;
import ca.college.zackeryazk.database.DatabaseAccess;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

	private final DatabaseAccess databaseAccess;

	public HomeController(DatabaseAccess databaseAccess) {
		this.databaseAccess = databaseAccess;
	}

	@GetMapping("/")
	public String getAllBooks(Model model) {
		List<Book> books = databaseAccess.getAllBooks();
		model.addAttribute("books", books);
		return "index";
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@GetMapping("/logout")
	public String showLogoutPage() {
		return "redirect:/"; // Redirect to the home page after logout
	}
}
