package net.arville.repository;

import net.arville.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public List<Book> findBookByBookNameContaining(String bookName);

    public List<Book> findBookByAuthorContaining(String author);

    public List<Book> findBookByAuthorContainingAndBookNameContaining(String author, String bookName);
}
