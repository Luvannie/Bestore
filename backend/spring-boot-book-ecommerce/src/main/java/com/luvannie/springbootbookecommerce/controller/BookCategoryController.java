package com.luvannie.springbootbookecommerce.controller;


import com.luvannie.springbootbookecommerce.component.LocalizationUtils;
import com.luvannie.springbootbookecommerce.dto.BookCategoryDTO;
import com.luvannie.springbootbookecommerce.entity.BookCategory;
import com.luvannie.springbootbookecommerce.responses.ResponseObject;
import com.luvannie.springbootbookecommerce.service.BookCategory.BookCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.luvannie.springbootbookecommerce.utils.MessageKeys;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/bookcategories_admin")
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
    public ResponseEntity<ResponseObject> getAllBookCategories(
            @RequestParam("page")     int page,
            @RequestParam("limit")    int limit
    ) {
        List<BookCategory> bookCategories = bookCategoryService.getAllBookCategories();
        this.kafkaTemplate.send("get-all-bookcategories", bookCategories);
        return ResponseEntity.ok(ResponseObject.builder()
                        .message("Get list of book categories successfully")
                        .status(HttpStatus.OK)
                        .data(bookCategories)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBookCategoryById(
            @PathVariable("id") Long bookCategoryId
    ) {
        BookCategory existingBookCategory = bookCategoryService.getBookCategoryById(bookCategoryId);
        return ResponseEntity.ok(ResponseObject.builder()
                        .data(existingBookCategory)
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
        bookCategoryService.updateBookCategory(id, bookCategoryDTO);
        return ResponseEntity.ok(ResponseObject
                .builder()
                .data(bookCategoryService.getBookCategoryById(id))
                .message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_BOOKCATEGORY_SUCCESSFULLY))
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
