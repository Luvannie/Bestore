package com.luvannie.springbootbookecommerce.service.BookCategory;

import com.luvannie.springbootbookecommerce.dao.BookCategoryRepository;
import com.luvannie.springbootbookecommerce.dao.BookRepository;
import com.luvannie.springbootbookecommerce.dto.BookCategoryDTO;
import com.luvannie.springbootbookecommerce.entity.Book;
import com.luvannie.springbootbookecommerce.entity.BookCategory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCategoryService implements IBookCategoryService {
    private final BookCategoryRepository bookCategoryRepository;
    private final BookRepository bookRepository;
    @Override
    @Transactional
    public BookCategory createBookCategory(BookCategoryDTO bookCategoryDTO) {
        BookCategory newCategory = BookCategory
                .builder()
                .categoryName(bookCategoryDTO.getName())
                .build();
        return bookCategoryRepository.save(newCategory);
    }

    @Override
    public BookCategory getBookCategoryById(Long bookCategoryId) {
        // Implement the logic to get a book category by id

        return bookCategoryRepository.findById(bookCategoryId).orElse(null);
    }

    @Override
    public List<BookCategory> getAllBookCategories() {
        // Implement the logic to get all book categories
        return bookCategoryRepository.findAll();
    }

    @Override
    public BookCategory updateBookCategory(Long bookCategoryId, BookCategoryDTO bookCategory) {
        // Implement the logic to update a book category
        BookCategory bookCategory1 = getBookCategoryById(bookCategoryId);
        bookCategory1.setCategoryName(bookCategory.getName());
        bookCategoryRepository.save(bookCategory1);
        return bookCategory1;
    }

    @Override
    public BookCategory deleteBookCategory(Long bookCategoryId) throws Exception {
        // Implement the logic to delete a book category
        BookCategory bookCategory = bookCategoryRepository.findById(bookCategoryId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        List<Book> books = bookRepository.findByCategory(bookCategory);
        if(!books.isEmpty()){
            throw new IllegalStateException("Cannot delete category with associated books");
        }
        else{
            bookCategoryRepository.deleteById(bookCategoryId);
            return bookCategory;
        }



    }
}
