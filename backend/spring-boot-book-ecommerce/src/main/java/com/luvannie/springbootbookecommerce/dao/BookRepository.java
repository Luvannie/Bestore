package com.luvannie.springbootbookecommerce.dao;

import com.luvannie.springbootbookecommerce.entity.Book;
import com.luvannie.springbootbookecommerce.entity.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("http://localhost:4200")
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByCategoryId( @Param("id") Long id, Pageable pageable);
    Page<Book> findByTitleContaining(@Param("title") String title, Pageable pageable);

    boolean existsByTitle(String title);
    Page<Book> findAll(Pageable pageable);//phân trang
    List<Book> findByCategory(BookCategory category);

    Book findById(long id);

//    @Query("SELECT p FROM Book p JOIN p.favorites f WHERE f.user.id = :userId")
//    List<Book> findFavoriteBooksByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Book b WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR b.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR b.title LIKE %:keyword% OR b.description LIKE %:keyword%)")
    Page<Book> searchBooks
            (@Param("categoryId") Long categoryId,
             @Param("keyword") String keyword, Pageable pageable);
}

