package com.jb.couponsystem.repo;

import com.jb.couponsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailAndPassword(String email, String password);

    Optional<Customer> findByEmail(String email);
}
