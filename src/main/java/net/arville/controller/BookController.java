package net.arville.controller;

import net.arville.enumeration.ErrorCode;
import net.arville.enumeration.SpecificationOperation;
import net.arville.exception.ItemNotFoundException;
import net.arville.payload.*;
import net.arville.service.BookService;
import net.arville.model.Book;
import net.arville.util.SpecificationBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<ResponseBodyHandler> getBook(
            @RequestParam(name = "name", required = false) String bookName,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "start-date", required = false) String startDateStr,
            @RequestParam(name = "end-date", required = false) String endDateStr
    ) {
        ResponseBodyHandler responseBody;
        LocalDateTime startDate, endDate;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            SpecificationBuilder<Book> bookSpecBuilder = new SpecificationBuilder<>();

            if (bookName != null) {
                bookSpecBuilder.with("bookName", SpecificationOperation.LIKE, bookName);
            }

            if (author != null) {
                bookSpecBuilder.with("author", SpecificationOperation.LIKE, author);
            }

            if (startDateStr != null) {
                startDate = LocalDate.parse(startDateStr, dateFormat).atStartOfDay();
                bookSpecBuilder.with("createdAt", SpecificationOperation.GREATER_THAN_OR_EQUAL, startDate);
            }

            if (endDateStr != null) {
                endDate = LocalDate.parse(endDateStr, dateFormat).atStartOfDay();
                bookSpecBuilder.with("createdAt", SpecificationOperation.LESS_THAN_OR_EQUAL, endDate);
            }

            List<Book> books = bookService.getAllBookBy(bookSpecBuilder.build());

            responseBody = ErrorCode.SUCCESS.Response(books);
        } catch (ItemNotFoundException e) {
            responseBody = ErrorCode.NO_RESULT_FOUND.Response(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (DateTimeParseException e) {
            responseBody = ErrorCode.INVALID_DATE_FILTER.Response(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBodyHandler> getBookById(@PathVariable Long id) {
        ResponseBodyHandler responseBody;

        try {
            Book book = bookService.getBookById(id);
            responseBody = ErrorCode.SUCCESS.Response(book);
        } catch (ItemNotFoundException e) {
            responseBody = ErrorCode.NO_RESULT_FOUND.Response(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping()
    public ResponseEntity<ResponseBodyHandler> addBook(@RequestBody Book book) {
        ResponseBodyHandler responseBody;

        Book newBook = bookService.addBook(book);
        responseBody = ErrorCode.SUCCESS.Response(newBook);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBodyHandler> updateBook(@PathVariable Long id, @RequestBody Book book) {
        ResponseBodyHandler responseBody;

        try {
            UpdateResponse updatedBookResponse = bookService.updateBook(id, book);
            responseBody = ErrorCode.SUCCESS.Response(updatedBookResponse);
        } catch (ItemNotFoundException e) {
            responseBody = ErrorCode.NO_RESULT_FOUND.Response(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBodyHandler> deleteBook(@PathVariable Long id) {
        ResponseBodyHandler responseBody;

        try {
            Book deletedBook = bookService.deleteBookById(id);
            responseBody = ErrorCode.SUCCESS.Response(deletedBook);
        } catch (ItemNotFoundException e) {
            responseBody = ErrorCode.NO_RESULT_FOUND.Response(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
