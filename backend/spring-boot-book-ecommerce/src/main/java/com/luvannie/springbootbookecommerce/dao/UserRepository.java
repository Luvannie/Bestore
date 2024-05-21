package com.luvannie.springbootbookecommerce.dao;

import com.luvannie.springbootbookecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount(String account);

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    @Query("SELECT o FROM User o WHERE o.active = true AND (:keyword IS NULL OR :keyword = '' OR " +
            "o.username LIKE %:keyword% " +
            "OR o.phoneNumber LIKE %:keyword%) " +
            "AND LOWER(o.role.name) = 'user'")
    Page<User> findAll(@Param("keyword") String keyword, Pageable pageable);
    List<User> findByRoleId(Long roleId);

    boolean existsByAccount(String account);
}
