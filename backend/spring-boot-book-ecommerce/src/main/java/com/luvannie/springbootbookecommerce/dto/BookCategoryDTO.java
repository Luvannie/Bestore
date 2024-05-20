package com.luvannie.springbootbookecommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDTO {
    @NotEmpty(message = "Category's name cannot be empty")
    private String name;




}
