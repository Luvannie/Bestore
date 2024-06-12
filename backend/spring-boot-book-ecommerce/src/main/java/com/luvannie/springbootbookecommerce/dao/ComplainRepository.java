package com.luvannie.springbootbookecommerce.dao;

import com.luvannie.springbootbookecommerce.entity.Complain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComplainRepository extends JpaRepository<Complain, Long> {
    List<Complain> findByIsFinishIsFalse();
    Optional<Complain> findById(Long Id);

}
