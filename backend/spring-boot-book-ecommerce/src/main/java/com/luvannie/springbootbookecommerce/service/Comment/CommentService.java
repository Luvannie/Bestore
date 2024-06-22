package com.luvannie.springbootbookecommerce.service.Comment;

import com.github.javafaker.Faker;
import com.luvannie.springbootbookecommerce.dao.BookRepository;
import com.luvannie.springbootbookecommerce.dao.CommentRepository;
import com.luvannie.springbootbookecommerce.dao.UserRepository;
import com.luvannie.springbootbookecommerce.dto.CommentDTO;
import com.luvannie.springbootbookecommerce.entity.Book;
import com.luvannie.springbootbookecommerce.entity.Comment;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.responses.comment.CommentResponse;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService implements ICommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    @Override
    @Transactional
    public Comment insertComment(CommentDTO comment) {
        User user = userRepository.findById(comment.getUserId()).orElse(null);
        Book book = bookRepository.findById(comment.getBookId()).orElse(null);
        if (user == null || book == null) {
            throw new IllegalArgumentException("User or product not found");
        }
        Comment newComment = Comment.builder()
                .userId(user.getId())
                .bookId(book.getId())
                .content(comment.getContent())
                .build();
        return commentRepository.save(newComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void updateComment(Long id, CommentDTO commentDTO) throws DataNotFoundException {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Comment not found"));
        existingComment.setContent(commentDTO.getContent());
        commentRepository.save(existingComment);
    }

//    @Override
//    public List<CommentResponse> getCommentsByUserAndProduct(Long userId, Long bookId) {
//        List<Comment> comments = commentRepository.findByUserIdAndBookId(userId, bookId);
//        return comments.stream()
//                .map(comment -> CommentResponse.fromComment(comment))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<CommentResponse> getCommentsByProduct(Long productId) {
//        List<Comment> comments = commentRepository.findByBookId(productId);
//        return comments.stream()
//                .map(comment -> CommentResponse.fromComment(comment))
//                .collect(Collectors.toList());
//    }

    @Override
    public void generateFakeComments() throws Exception {
    Faker faker = new Faker();
    Random random = new Random();
    // Get all users
    List<User> users = userRepository.findAll();
    // Get all books
    List<Book> books = bookRepository.findAll();
    List<Comment> comments = new ArrayList<>();
    final int totalRecords = 10_000;
    final int batchSize = 1000;
    for (int i = 0; i < totalRecords; i++) {

        // Select a random user and book
        User user = users.get(random.nextInt(users.size()));
        Book book = books.get(random.nextInt(books.size()));

        // Generate a fake comment
        Comment comment = Comment.builder()
                .content(faker.lorem().sentence())
                .bookId(book.getId())
                .userId(user.getId())
                .build();

        // Save the comment
        comments.add(comment);
        if(comments.size() >= batchSize) {
            commentRepository.saveAll(comments);
            comments.clear();
        }
    }
    if(!comments.isEmpty()) {
        commentRepository.saveAll(comments);
    }
}
}
