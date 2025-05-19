package com.exam.demo.repositories;


import com.exam.demo.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {

} 