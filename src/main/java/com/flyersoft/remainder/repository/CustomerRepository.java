package com.flyersoft.remainder.repository;

import com.flyersoft.remainder.model.Customer;
import com.flyersoft.remainder.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT d FROM Document d WHERE d.customer.id = :customerId AND d.expiryDate = (SELECT MIN(d2.expiryDate) FROM Document d2 WHERE d2.customer.id = :customerId)")
    Document findNearestExpiryDocumentByCustomerId(@Param("customerId") Long customerId);
}
