package net.arville.controller;

import net.arville.service.BookService;
import net.arville.model.Book;
import net.arville.util.UpdateResponse;
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
    public List<Book> getBook(
            @RequestParam(name = "name", required = false) String bookName,
            @RequestParam(name = "author", required = false) String author
    ) {
        if (author == null && bookName != null) {
            return bookService.getBookByBookName(bookName);
        } else if (author != null && bookName == null) {
            return bookService.getBookByAuthor(author);
        } else if (author != null && bookName != null) {
            return bookService.getBookByBookNameAndAuthor(bookName, author);
        }
        return bookService.getAllBook();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping()
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public UpdateResponse updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public Book deleteBook(@PathVariable Long id) {
        return bookService.deleteBookById(id);
    }

}
