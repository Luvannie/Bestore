package com.luvannie.springbootbookecommerce.responses.bookCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCategoryListResponse {
    private List<BookCategoryResponse> bookCategories;
    private int totalPages;
}
