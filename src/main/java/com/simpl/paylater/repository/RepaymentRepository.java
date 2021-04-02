package com.simpl.paylater.repository;

import com.simpl.paylater.model.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment,Long> {
}
