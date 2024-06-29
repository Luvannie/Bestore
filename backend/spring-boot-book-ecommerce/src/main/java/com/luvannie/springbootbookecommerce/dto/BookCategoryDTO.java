package com.luvannie.springbootbookecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDTO {
    @NotEmpty(message = "Category's name cannot be empty")
    @JsonProperty("categoryName")
    private String categoryName;
}
