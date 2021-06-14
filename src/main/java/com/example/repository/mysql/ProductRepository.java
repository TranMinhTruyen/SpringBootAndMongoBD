package com.example.repository.mysql;

import com.example.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tran Minh Truyen
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	Product findProductById(int id);
	Product findProductByName(String name);
}
