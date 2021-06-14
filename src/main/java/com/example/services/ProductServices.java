package com.example.services;

import com.example.common.entity.Product;
import com.example.common.request.ProductRequest;
import com.example.repository.mysql.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */

@Service
public class ProductServices {

	@Autowired
	private ProductRepository productRepository;

	public boolean createProduct(ProductRequest productRequest){
		if (productRequest != null){
			Product newProduct = new Product();
			newProduct.setName(productRequest.getName());
			newProduct.setPrice(productRequest.getPrice());
			newProduct.setType(productRequest.getType());
			newProduct.setImage(productRequest.getImage());
			productRepository.save(newProduct);
			return true;
		}
		else return false;
	}


	public List getAllProduct(){
		return productRepository.findAll();
	}
}
