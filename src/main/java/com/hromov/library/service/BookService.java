package com.hromov.library.service;

import com.hromov.library.model.Book;

import java.util.List;

public interface BookService {
    List<String> getStrippedBooks();
    List<Book> getBooks();

    List<Book> getFavoriteBooks(Long userId);

    Book getBookByName(String name);

    void addBook(Book book);

    void addBookToFavorites(Long userId, Long bookId);

    void removeBook(Long id);
}
