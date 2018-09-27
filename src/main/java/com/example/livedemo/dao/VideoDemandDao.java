package com.example.livedemo.dao;

import com.example.livedemo.entity.VideoDemand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDemandDao extends JpaRepository<VideoDemand, Long> {
    Page<VideoDemand> findAll(Pageable pageable);

    Page<VideoDemand> findByType(String type, Pageable pageable);
}
