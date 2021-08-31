package com.core.repository.mysql;


import com.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tran Minh Truyen
 */
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
	Product findProductById(int id);
	List findProductByNameContains(String name);
	List findAllByBrandId(int brand_id);
	List findAllByCategoryId(int category_id);
}
