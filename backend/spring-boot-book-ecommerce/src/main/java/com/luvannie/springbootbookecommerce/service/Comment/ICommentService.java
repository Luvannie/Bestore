package com.luvannie.springbootbookecommerce.service.Comment;

import com.luvannie.springbootbookecommerce.dto.CommentDTO;
import com.luvannie.springbootbookecommerce.entity.Comment;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.responses.comment.CommentResponse;

import java.util.List;

public interface ICommentService {
    Comment insertComment(CommentDTO comment);

    void deleteComment(Long commentId);
    void updateComment(Long id, CommentDTO commentDTO) throws DataNotFoundException;

//    List<CommentResponse> getCommentsByUserAndProduct(Long userId, Long productId);
//    List<CommentResponse> getCommentsByProduct(Long productId);
    void generateFakeComments() throws Exception;
}
