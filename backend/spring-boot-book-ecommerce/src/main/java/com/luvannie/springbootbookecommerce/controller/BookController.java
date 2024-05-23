package com.luvannie.springbootbookecommerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luvannie.springbootbookecommerce.component.SecurityUtils;
import com.luvannie.springbootbookecommerce.dto.BookDTO;
import com.luvannie.springbootbookecommerce.entity.Book;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.responses.ResponseObject;
import com.luvannie.springbootbookecommerce.responses.book.BookListResponse;
import com.luvannie.springbootbookecommerce.responses.book.BookResponse;
import com.luvannie.springbootbookecommerce.service.book.BookService;
import com.luvannie.springbootbookecommerce.service.book.IBookRedisService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/books_admin")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final IBookRedisService bookRedisService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final SecurityUtils securityUtils;

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) throws Exception {
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBookById(@PathVariable Long id) throws Exception {
        Book existingBook = bookService.getBookById(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(BookResponse.fromBook(existingBook))
                .message("Get detail book successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllBooks(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws JsonProcessingException {
        int totalPages = 0;
        //bookRedisService.clear();
        // Create Pageable from page and limit information
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sort.by("createdAt").descending()
                Sort.by("id").ascending()
        );
        logger.info(String.format("keyword = %s, category_id = %d, page = %d, limit = %d",
                keyword, categoryId, page, limit));
        List<BookResponse> bookResponses = bookRedisService
                .getAllBooks(keyword, categoryId, pageRequest);
        if (bookResponses!=null && !bookResponses.isEmpty()) {
            totalPages = bookResponses.get(0).getTotalPages();
        }
        if(bookResponses == null) {
            Page<BookResponse> bookPage = bookService
                    .getAllBooks(keyword, categoryId, pageRequest);
            // Get total number of pages
            totalPages = bookPage.getTotalPages();
            bookResponses = bookPage.getContent();
            // Add totalPages to each BookResponse object
            for (BookResponse book : bookResponses) {
                book.setTotalPages(totalPages);
            }
            bookRedisService.saveAllBooks(
                    bookResponses,
                    keyword,
                    categoryId,
                    pageRequest
            );
        }
        BookListResponse bookListResponse = BookListResponse
                .builder()
                .books(bookResponses)
                .totalPages(totalPages)
                .build();
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Get books successfully")
                .status(HttpStatus.OK)
                .data(bookListResponse)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateBook(
            @PathVariable long id,
            @RequestBody BookDTO bookDTO) throws Exception {
        Book updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(BookResponse.fromBook(updatedBook))
                .message("Update book successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(null)
                .message(String.format("Book with id = %d deleted successfully", id))
                .status(HttpStatus.OK)
                .build());
    }

    @PostMapping("/like/{bookId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> likeBook(@PathVariable Long bookId) throws Exception {
        User loginUser = securityUtils.getLoggedInUser();
        Book likedBook = bookService.likeBook(loginUser.getId(), bookId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(BookResponse.fromBook(likedBook))
                .message("Like book successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @PostMapping("/unlike/{bookId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> unlikeBook(@PathVariable Long bookId) throws Exception {
        User loginUser = securityUtils.getLoggedInUser();
        Book unlikedBook = bookService.unlikeBook(loginUser.getId(), bookId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(BookResponse.fromBook(unlikedBook))
                .message("Unlike book successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/favorites")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> findFavoriteBooksByUserId() throws Exception {
        User loginUser = securityUtils.getLoggedInUser();
        List<BookResponse> favoriteBooks = bookService.findFavoriteBooksByUserId(loginUser.getId());
        return ResponseEntity.ok(ResponseObject.builder()
                .data(favoriteBooks)
                .message("Favorite books retrieved successfully")
                .status(HttpStatus.OK)
                .build());
    }
}