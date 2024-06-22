package com.luvannie.springbootbookecommerce.responses.favorite;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.entity.Favorite;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("user_id")
    private Long userId;

    public static FavoriteResponse fromFavorite(Favorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.getId())
                .bookId(favorite.getBook_id())
                .userId(favorite.getUser_id())
                .build();
    }
}
