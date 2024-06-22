package com.luvannie.springbootbookecommerce.responses.complain;

import com.luvannie.springbootbookecommerce.responses.book.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplainListResponse {
    private List<ComplainResponse> complains;
    private int totalPages;
}
