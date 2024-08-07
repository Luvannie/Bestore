package com.luvannie.springbootbookecommerce.service.bookCategory;

import com.luvannie.springbootbookecommerce.dto.BookCategoryDTO;
import com.luvannie.springbootbookecommerce.entity.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IBookCategoryService {
    BookCategory createBookCategory(BookCategoryDTO bookCategory);
    BookCategory getBookCategoryById(Long bookCategoryId);
    Page<BookCategory> getAllBookCategories(PageRequest pageRequest);

    BookCategory updateBookCategory(Long bookCategoryId, BookCategoryDTO bookCategory);
    BookCategory deleteBookCategory(Long bookCategoryId) throws Exception;

}
