package net.arville.util;

import net.arville.model.Book;

public class UpdateResponse {
    private Book oldBook;
    private Book updatedBook;

    public Book getOldBook() {
        return oldBook;
    }

    public void setOldBook(Book oldBook) {
        this.oldBook = oldBook;
    }

    public Book getUpdatedBook() {
        return updatedBook;
    }

    public void setUpdatedBook(Book updatedBook) {
        this.updatedBook = updatedBook;
    }

    public UpdateResponse(Book oldBook, Book updatedBook) {
        this.oldBook = oldBook;
        this.updatedBook = updatedBook;
    }
}
