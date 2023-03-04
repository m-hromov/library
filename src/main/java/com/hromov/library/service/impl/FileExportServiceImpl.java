package com.hromov.library.service.impl;

import com.hromov.library.model.Book;
import com.hromov.library.model.EGenre;
import com.hromov.library.service.BookService;
import com.hromov.library.service.FileExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileExportServiceImpl implements FileExportService {
    public static final String TEXT_CSV = "text/csv";
    public static final String BOOK_FILE = "books.csv";
    private final BookService bookService;

    @Override
    public void exportBooks(HttpServletResponse response) {
        response.setContentType(TEXT_CSV);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename\"%s\"", BOOK_FILE));
        try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT)) {
            List<Book> books = bookService.getBooks();
            csvPrinter.printRecords(convertBooks(books));
        } catch (IOException e) {
            log.error("Errow exporting csv.");
            throw new RuntimeException(e);
        }
        log.info("Books were successfully exported.");
    }

    protected List<String[]> convertBooks(List<Book> books) {
        return books.stream()
                .map(book -> {
                    String[] cells = new String[5];
                    cells[0] = String.valueOf(book.getId());
                    cells[1] = book.getName();
                    cells[2] = book.getDescription();
                    cells[3] = book.getGenres()
                            .stream()
                            .map(EGenre::name)
                            .collect(Collectors.joining(","));
                    cells[4] = book.getAuthors()
                            .stream()
                            .map(author -> String.format("[%d, %s]", author.getId(), author.getName()))
                            .collect(Collectors.joining(","));
                    return cells;
                })
                .collect(Collectors.toList());
    }
}
