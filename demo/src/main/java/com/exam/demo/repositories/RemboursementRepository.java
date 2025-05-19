package com.exam.demo.repositories;

import com.exam.demo.entities.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {

} 