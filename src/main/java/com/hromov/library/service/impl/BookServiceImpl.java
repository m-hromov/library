package com.hromov.library.service.impl;

import com.hromov.library.exception.NotFoundException;
import com.hromov.library.model.Book;
import com.hromov.library.model.User;
import com.hromov.library.repository.BookRepository;
import com.hromov.library.service.BookService;
import com.hromov.library.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final UserService userService;

    @Override
    public List<String> getStrippedBooks() {
        return bookRepository.findAll()
                .stream()
                .map(Book::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getFavoriteBooks(Long userId) {
        return userService.getUserById(userId)
                .getBooks();
    }

    @Override
    public Book getBookByName(String name) {
        return bookRepository.findByName(name)
                .orElseThrow(() -> {
                    throw new NotFoundException("Book was not found.");
                });
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void addBookToFavorites(Long userId, Long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    throw new NotFoundException("Book was not found.");
                });
        user.getBooks().add(book);
    }

    @Override
    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }
}
