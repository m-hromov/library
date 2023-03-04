package com.hromov.library.controller;

import com.hromov.library.model.Book;
import com.hromov.library.service.BookService;
import com.hromov.library.service.FileExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hromov.library.controller.BookController.BOOK_BASE;

@RestController
@RequestMapping(BOOK_BASE)
@RequiredArgsConstructor
public class BookController {
    public static final String BOOK_BASE = "books";
    public static final String FAVORITE_BOOKS = "favorites/{userId}";
    public static final String GET_BOOK_BY_NAME = "/name";
    public static final String DOWNLOAD_BOOKS = "books.csv";
    public static final String ADD_BOOK = "new";
    public static final String ADD_BOOK_TO_FAVORITES = "favorites/{userId}/new";
    public static final String REMOVE_BOOK = "remove/{bookId}";
    public final BookService bookService;
    public final FileExportService fileExportService;

    @GetMapping
    public List<String> getBooks() {
        return bookService.getStrippedBooks();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(FAVORITE_BOOKS)
    public List<Book> getFavoriteBooks(@PathVariable("userId") Long userId) {
        return bookService.getFavoriteBooks(userId);
    }

    @GetMapping(GET_BOOK_BY_NAME)
    public Book getBookByName(@RequestParam("name") String name) {
        return bookService.getBookByName(name);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(DOWNLOAD_BOOKS)
    public void downloadBooks(HttpServletResponse response) {
        fileExportService.exportBooks(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(ADD_BOOK)
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(ADD_BOOK_TO_FAVORITES)
    public void addBookToFavorites(@PathVariable("userId") Long userId,
                                   @RequestParam("bookId") Long bookId) {
        bookService.addBookToFavorites(userId, bookId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(REMOVE_BOOK)
    public void removeBook(@PathVariable("bookId") Long bookId) {
        bookService.removeBook(bookId);
    }
}
