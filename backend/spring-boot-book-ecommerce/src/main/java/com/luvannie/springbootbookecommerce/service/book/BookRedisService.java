package com.luvannie.springbootbookecommerce.service.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luvannie.springbootbookecommerce.responses.book.BookResponse;
import com.luvannie.springbootbookecommerce.service.book.IBookRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookRedisService implements IBookRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    @Value("${spring.data.redis.use-redis-cache}")
    private boolean useRedisCache;

    private String getKeyFrom(String keyword,
                              Long categoryId,
                              PageRequest pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Sort sort = pageRequest.getSort();
        String sortDirection = sort.getOrderFor("id")
                .getDirection() == Sort.Direction.ASC ? "asc": "desc";
        String key = String.format("all_books:%s:%d:%d:%d:%s",
                keyword, categoryId, pageNumber, pageSize, sortDirection);
        return key;
    }

    @Override
    public List<BookResponse> getAllBooks(String keyword,
                                          Long categoryId,
                                          PageRequest pageRequest) throws JsonProcessingException {

        if(useRedisCache == false) {
            return null;
        }
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);
        List<BookResponse> bookResponses =
                json != null ?
                        redisObjectMapper.readValue(json, new TypeReference<List<BookResponse>>() {})
                        : null;
        return bookResponses;
    }

    @Override
    public void clear(){
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    //save to Redis
    public void saveAllBooks(List<BookResponse> bookResponses,
                             String keyword,
                             Long categoryId,
                             PageRequest pageRequest) throws JsonProcessingException {
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        String json = redisObjectMapper.writeValueAsString(bookResponses);
        redisTemplate.opsForValue().set(key, json);
    }
}
