package com.luvannie.springbootbookecommerce.service.book;

import com.github.javafaker.Faker;
import com.luvannie.springbootbookecommerce.dao.*;
import com.luvannie.springbootbookecommerce.dto.BookDTO;
import com.luvannie.springbootbookecommerce.entity.*;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.responses.book.BookResponse;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;

    @Override
    public Book createBook(BookDTO bookDTO) throws Exception {
        BookCategory existingBookCategory = bookCategoryRepository
                .findById(bookDTO.getBookCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find book category with id: "+bookDTO.getBookCategoryId()));

        Book newBook = Book.builder()
                .title(bookDTO.getTitle())
                .unitPrice(bookDTO.getPrice())
                .thumbnail(bookDTO.getThumbnail())
                .description(bookDTO.getDescription())
                .category(existingBookCategory)
                .build();
        return bookRepository.save(newBook);
    }

    @Override
    public Book getBookById(long id) throws Exception {
        Optional<Book> optionalBook = Optional.ofNullable(bookRepository.findById(id));
        if(optionalBook.isPresent()) {
            return optionalBook.get();
        }
        throw new DataNotFoundException("Cannot find book with id =" + id);
    }

    @Override
    public Page<BookResponse> getAllBooks(String keyword,
                                          Long categoryId, PageRequest pageRequest) {
        // Retrieve the list of books by page, limit, and categoryId (if provided)
        Page<Book> booksPage;
        booksPage = bookRepository.searchBooks(categoryId, keyword, pageRequest);
        return booksPage.map(BookResponse::fromBook);
    }

    @Override
    @Transactional
    public Book updateBook(long id, BookDTO bookDTO) throws Exception {
        Book existingBook = getBookById(id);
        if(existingBook != null) {
            //copy các thuộc tính từ DTO -> Book
            //Có thể sử dụng ModelMapper
//            BookCategory existingBookCategory = bookCategoryRepository
//                    .findById(bookDTO.getBookCategoryId())
//                    .orElseThrow(() ->
//                            new DataNotFoundException(
//                                    "Cannot find book category with id: "+bookDTO.getBookCategoryId()));
            if(bookDTO.getTitle() != null && !bookDTO.getTitle().isEmpty()) {
                existingBook.setTitle(bookDTO.getTitle());
            }

//            existingBook.setCategory(existingBookCategory);
            if(bookDTO.getPrice() >= 0) {
                existingBook.setUnitPrice(bookDTO.getPrice());
            }
            if(bookDTO.getDescription() != null &&
                    !bookDTO.getDescription().isEmpty()) {
                existingBook.setDescription(bookDTO.getDescription());
            }
            if(bookDTO.getThumbnail() != null &&
                    !bookDTO.getThumbnail().isEmpty()) {
                existingBook.setThumbnail(bookDTO.getThumbnail());
            }
            return bookRepository.save(existingBook);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        Optional<Book> optionalBook = Optional.ofNullable(bookRepository.findById(id));
        optionalBook.ifPresent(bookRepository::delete);
    }

    @Override
    public boolean existsByTitle(String title) {
        return bookRepository.existsByTitle(title);
    }

    @Override
    public List<Book> findBooksByIds(List<Long> bookIds) {
        return List.of();
    }

//
//    @Override
//    @Transactional
//    public Book likeBook(Long userId, Long bookId) throws Exception {
//    // Check if the user and book exist
//        if (!userRepository.existsById(userId) || !bookRepository.existsById(bookId)) {
//            throw new DataNotFoundException("User or book not found");
//        }
//
//    // Check if the user has already liked the book
//        if (favoriteRepository.existsByUserIdAndBookId(userId, bookId)) {
//        //throw new DataNotFoundException("Book already liked by the user");
//         } else {
//        // Create a new favorite entry and save it
//            Favorite favorite = Favorite.builder()
//                .book_id(bookId)
//                .user_id(userId)
//                .build();
//        favoriteRepository.save(favorite);
//    }
//    // Return the liked book
//    return bookRepository.findById(bookId).orElse(null);
//    }

//    @Override
//    @Transactional
//    public Book unlikeBook(Long userId, Long bookId) throws Exception {
//        // Check if the user and book exist
//        if (!userRepository.existsById(userId) || !bookRepository.existsById(bookId)) {
//            throw new DataNotFoundException("User or book not found");
//        }
//
//        // Check if the user has already liked the book
//        if (favoriteRepository.existsByUserIdAndBookId(userId, bookId)) {
//            Favorite favorite = favoriteRepository.findByUserIdAndBookId(userId, bookId);
//            favoriteRepository.delete(favorite);
//        }
//        return bookRepository.findById(bookId).orElse(null);
//    }

//    @Override
//    @Transactional
//    public List<BookResponse> findFavoriteBooksByUserId(Long userId) throws Exception {
//        // Validate the userId
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isEmpty()) {
//            throw new Exception("User not found with ID: " + userId);
//        }
//        // Retrieve favorite books for the given userId
//        List<Book> favoriteBooks = bookRepository.findFavoriteBooksByUserId(userId);
//        // Convert Book entities to BookResponse objects
//        return favoriteBooks.stream()
//                .map(BookResponse::fromBook)
//                .collect(Collectors.toList());
//    }

//    @Override
//
//    public void generateFakeLikes() throws Exception {
//        Faker faker = new Faker();
//        Random random = new Random();
//
//    // Get all users
//        List<User> users = userRepository.findAll();
//    // Get all books
//        List<Book> books = bookRepository.findAll();
//        final int totalRecords = 1_000;
//        final int batchSize = 100;
//        List<Favorite> favorites = new ArrayList<>();
//        for (int i = 0; i < totalRecords; i++) {
//        // Select a random user and book
//            User user = users.get(random.nextInt(users.size()));
//            Book book = books.get(random.nextInt(books.size()));
//
//        // Check if the user has already liked the book
//            if (!favoriteRepository.existsByUserIdAndBookId(user.getId(), book.getId())) {
//            // Generate a fake favorite
//                Favorite favorite = Favorite.builder()
//                    .user_id(user.getId())
//                    .book_id(book.getId())
//                    .build();
//                favorites.add(favorite);
//            }
//            if(favorites.size() >= batchSize) {
//                favoriteRepository.saveAll(favorites);
//                favorites.clear();
//            }
//        }
//        if(!favorites.isEmpty()) {
//            favoriteRepository.saveAll(favorites);
//        }
//    }
}

