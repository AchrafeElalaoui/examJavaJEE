package com.exam.demo.repositories;

import com.exam.demo.entities.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {
    Remboursement findById(long id);


    List<Remboursement> findByCreditId(Long creditId);
} 