package com.luvannie.springbootbookecommerce.service.book;

import com.luvannie.springbootbookecommerce.dto.BookDTO;
import com.luvannie.springbootbookecommerce.entity.Book;
import com.luvannie.springbootbookecommerce.responses.book.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookService {
    Book createBook(BookDTO bookDTO) throws Exception;
    Book getBookById(long id) throws Exception;
    Page<BookResponse> getAllBooks(String keyword, Long categoryId, PageRequest pageRequest);
    Book updateBook(long id, BookDTO bookDTO) throws Exception;
    void deleteBook(long id);
    boolean existsByTitle(String title);


    List<Book> findBooksByIds(List<Long> bookIds);
    //String storeFile(MultipartFile file) throws IOException; //chuyển sang FileUtils
    //void deleteFile(String filename) throws IOException;

//    Book likeBook(Long userId, Long bookId) throws Exception;
//    Book unlikeBook(Long userId, Long bookId) throws Exception;
//    List<BookResponse> findFavoriteBooksByUserId(Long userId) throws Exception;
//    void generateFakeLikes() throws Exception;
}
