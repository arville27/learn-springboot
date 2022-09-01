package net.arville.controller;

import net.arville.model.BookActivity;
import net.arville.service.BookActivityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book-activity")
public class BookActivityController {

    private final BookActivityService bookActivityService;

    public BookActivityController(BookActivityService bookActivityService) {
        this.bookActivityService = bookActivityService;
    }

    @GetMapping()
    public List<BookActivity> getAllBookActivity() {
        return bookActivityService.getAllBookActivity();
    }
}
