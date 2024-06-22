package com.luvannie.springbootbookecommerce.controller;


import com.luvannie.springbootbookecommerce.component.LocalizationUtils;
import com.luvannie.springbootbookecommerce.dto.BookCategoryDTO;
import com.luvannie.springbootbookecommerce.entity.BookCategory;
import com.luvannie.springbootbookecommerce.responses.ResponseObject;
import com.luvannie.springbootbookecommerce.responses.bookCategory.BookCategoryListResponse;
import com.luvannie.springbootbookecommerce.responses.bookCategory.BookCategoryResponse;
import com.luvannie.springbootbookecommerce.service.BookCategory.BookCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.luvannie.springbootbookecommerce.utils.MessageKeys;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book_categories_admin")
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;
    private final LocalizationUtils localizationUtils;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> createBookCategory(
            BookCategoryDTO bookCategoryDTO,
            BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message(errorMessages.toString())
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());

        }
        BookCategory bookCategory = bookCategoryService.createBookCategory(bookCategoryDTO);
        this.kafkaTemplate.send("insert-a-bookcategory", bookCategory);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Create book category successfully")
                .status(HttpStatus.OK)
                .data(bookCategory)
                .build());
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> getAllBookCategories(
            @RequestParam(defaultValue = "0")     int page,
            @RequestParam(defaultValue = "10")    int limit
    ) {
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").ascending()
        );
        Page<BookCategory> bookCategoryPage = bookCategoryService.getAllBookCategories(pageRequest);
        // Lấy tổng số trang
        int totalPages = bookCategoryPage.getTotalPages();

        List<BookCategory> bookCategories = bookCategoryPage.getContent();
        List<BookCategoryResponse> bookCategoryResponses = bookCategories.stream()
                .map(BookCategoryResponse::fromBookCategory)
                .collect(Collectors.toList());

        // Add totalPages to each BookCategoryResponse object
        for (BookCategoryResponse bookCategory : bookCategoryResponses) {
            bookCategory.setTotalPages(totalPages);
        }
        BookCategoryListResponse bookCategoryListResponse = BookCategoryListResponse
                .builder()
                .bookCategories(bookCategoryResponses)
                .totalPages(totalPages)
                .build();

//        this.kafkaTemplate.send("get-all-bookcategories", bookCategories);
        return ResponseEntity.ok(ResponseObject.builder()
                        .message("Get list of book categories successfully")
                        .status(HttpStatus.OK)
                        .data(bookCategoryListResponse)
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> getBookCategoryById(
            @PathVariable("id") Long bookCategoryId
    ) {
        BookCategory existingBookCategory = bookCategoryService.getBookCategoryById(bookCategoryId);
        BookCategoryResponse bookCategoryResponse = BookCategoryResponse.fromBookCategory(existingBookCategory);
        return ResponseEntity.ok(ResponseObject.builder()
                        .data(bookCategoryResponse)
                        .message("Get book category information successfully")
                        .status(HttpStatus.OK)
                .build());
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateBookCategory(
            @PathVariable Long id,
            @RequestBody BookCategoryDTO bookCategoryDTO
    ) {
        BookCategory updatedBookCategory =bookCategoryService.updateBookCategory(id, bookCategoryDTO);
        BookCategoryResponse bookCategoryResponse = BookCategoryResponse.fromBookCategory(updatedBookCategory);
        return ResponseEntity.ok(ResponseObject
                .builder()
                .data(bookCategoryResponse)
                .message("Update book category successfully")
                .status(HttpStatus.OK)
                .build());
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteBookCategory(@PathVariable Long id) throws Exception{
        bookCategoryService.deleteBookCategory(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Delete book category successfully")
                        .build());
    }
}
