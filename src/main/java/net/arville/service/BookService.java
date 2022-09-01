package net.arville.service;

import net.arville.exception.ItemNotFoundException;
import net.arville.model.Book;
import net.arville.repository.BookRepository;
import net.arville.util.UpdateResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public List<Book> getBookByBookName(String bookName) {
        return bookRepository.findBookByBookNameContaining(bookName);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBookByAuthor(String author) {
        return bookRepository.findBookByAuthorContaining(author);
    }

    public List<Book> getBookByBookNameAndAuthor(String bookName, String author) {
        return bookRepository.findBookByAuthorContainingAndBookNameContaining(author, bookName);
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new ItemNotFoundException("Book is not exist");
        }
    }

    @Transactional
    public UpdateResponse updateBook(Long id, Book newBookData) {
        String newName = newBookData.getBookName();
        String newAuthor = newBookData.getAuthor();
        Integer newPrice = newBookData.getPrice();

        Book updatedBook = this.getBookById(id);
        Book oldBook;

        try {
            oldBook = (Book) updatedBook.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        if (newName != null && newName.length() > 0 && !Objects.equals(newName, updatedBook.getBookName())) {
            updatedBook.setBookName(newName);
        }

        if (newAuthor != null && newAuthor.length() > 0 && !Objects.equals(newAuthor, updatedBook.getAuthor())) {
            updatedBook.setAuthor(newAuthor);
        }

        if (newPrice != null && !Objects.equals(newPrice, updatedBook.getPrice())) {
            updatedBook.setPrice(newPrice);
        }

        return new UpdateResponse(oldBook, updatedBook);
    }

    public Book deleteBookById(Long id) {
        Book book = this.getBookById(id);
        bookRepository.deleteById(id);
        return book;
    }
}
