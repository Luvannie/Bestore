package com.luvannie.springbootbookecommerce.responses.bookCategory;

import com.luvannie.springbootbookecommerce.entity.BookCategory;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCategoryResponse {
    private Long id;
    private String categoryName;
    private int totalPages;

    public static BookCategoryResponse fromBookCategory(BookCategory bookCategory) {
        return BookCategoryResponse.builder()
                .id(bookCategory.getId())
                .categoryName(bookCategory.getCategoryName())
                .totalPages(0)
                .build();
    }
}
