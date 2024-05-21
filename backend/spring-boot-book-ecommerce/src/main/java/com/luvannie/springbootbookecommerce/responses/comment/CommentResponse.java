package com.luvannie.springbootbookecommerce.responses.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.entity.Comment;
import com.luvannie.springbootbookecommerce.responses.BaseResponse;
import com.luvannie.springbootbookecommerce.responses.user.UserResponse;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse extends BaseResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("user")
    private UserResponse user;

    @JsonProperty("book_id")
    private Long bookId;

    public static CommentResponse fromComment(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(UserResponse.fromUser(comment.getUser()))
                .bookId(comment.getBook().getId())
                .build();
    }
}
