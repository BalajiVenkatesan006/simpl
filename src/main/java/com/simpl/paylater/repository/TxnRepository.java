package com.simpl.paylater.repository;

import com.simpl.paylater.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TxnRepository extends JpaRepository<Txn,Long> {
    List<Txn> findByMerchantId(Long merchantId);
}
