package com.luvannie.springbootbookecommerce.service.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luvannie.springbootbookecommerce.responses.book.BookResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IBookRedisService {
    // Clear cached data in Redis
    void clear(); // clear cache
    List<BookResponse> getAllBooks(
            String keyword,
            Long categoryId, PageRequest pageRequest) throws JsonProcessingException;
    void saveAllBooks(List<BookResponse> bookResponses,
                      String keyword,
                      Long categoryId,
                      PageRequest pageRequest) throws JsonProcessingException;
}
