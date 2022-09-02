package net.arville.controller;

import net.arville.exception.ItemNotFoundException;
import net.arville.payload.*;
import net.arville.service.BookService;
import net.arville.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(name = "author", required = false) String author
    ) {
        ResponseBodyHandler responseBody;

        try {
            List<Book> books;

            if (author == null && bookName != null) {
                books = bookService.getBookByBookName(bookName);
            } else if (author != null && bookName == null) {
                books = bookService.getBookByAuthor(author);
            } else if (author != null && bookName != null) {
                books = bookService.getBookByBookNameAndAuthor(bookName, author);
            } else {
                books = bookService.getAllBook();
            }

            responseBody = ErrorCode.SUCCESS.Response(books);
        } catch (ItemNotFoundException e) {
            responseBody = ErrorCode.NO_RESULT_FOUND.Response(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
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
