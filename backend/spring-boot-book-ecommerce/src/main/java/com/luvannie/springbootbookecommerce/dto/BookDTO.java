package com.luvannie.springbootbookecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {


    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("book_category_id")
    private Long BookCategoryId;
}