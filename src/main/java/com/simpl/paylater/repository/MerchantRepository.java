package com.simpl.paylater.repository;

import com.simpl.paylater.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Long> {
    public Merchant findByName(String name);
}
