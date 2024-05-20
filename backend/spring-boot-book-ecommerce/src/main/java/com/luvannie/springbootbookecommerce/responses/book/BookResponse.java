package com.luvannie.springbootbookecommerce.responses.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.entity.Book;
import com.luvannie.springbootbookecommerce.entity.Comment;
import com.luvannie.springbootbookecommerce.entity.Favorite;
import com.luvannie.springbootbookecommerce.responses.BaseResponse;
import com.luvannie.springbootbookecommerce.responses.comment.CommentResponse;
import com.luvannie.springbootbookecommerce.responses.favorite.FavoriteResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse extends BaseResponse {
    private Long id;
    private String title;
    private Float unitPrice;
    private String thumbnail;
    private String description;
    // Thêm trường totalPages
    private int totalPages;

    @JsonProperty("comments")
    private List<CommentResponse> comments = new ArrayList<>();

    @JsonProperty("favorites")
    private List<FavoriteResponse> favorites = new ArrayList<>();

    @JsonProperty("category_id")
    private Long categoryId;

   public static BookResponse fromBook(Book book) {
        List<Comment> comments = book.getComments().stream().sorted(Comparator.comparing(Comment::getDateCreated).reversed()) // Sort comments by createdAt in descending order
                .collect(Collectors.toList());
        List<Favorite> favorites = book.getFavorites();
        BookResponse bookResponse = BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .unitPrice(book.getUnitPrice())
                .thumbnail(book.getThumbnail())
                .description(book.getDescription())
                .categoryId(book.getCategory().getId())
                .comments(comments.stream().map(CommentResponse::fromComment).toList())
                .favorites(favorites.stream().map(FavoriteResponse::fromFavorite).toList())
                .totalPages(0)
                .build();

       Date date = book.getDateCreated();
       LocalDateTime createdDateTime = date.toInstant()
               .atZone(ZoneId.systemDefault())
               .toLocalDateTime();
       Date date1 = book.getLastUpdated();
       LocalDateTime lastUpdateDateTime = date.toInstant()
               .atZone(ZoneId.systemDefault())
               .toLocalDateTime();
        bookResponse.setDateCreated(createdDateTime);
        bookResponse.setLastUpdated(lastUpdateDateTime);
        return bookResponse;
    }
}
