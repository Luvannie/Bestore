package com.luvannie.springbootbookecommerce.dao;

import com.luvannie.springbootbookecommerce.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
    Favorite findByUserIdAndBookId(Long userId, Long bookId);

}
