package com.jb.couponsystem.repo;

import com.jb.couponsystem.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    /*Add, Update, Delete, Get*/

    Optional<Company> findByEmailAndPassword(String email, String password);

    Optional<Company> findByEmail(String email);
}
