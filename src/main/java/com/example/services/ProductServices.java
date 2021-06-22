package com.example.services;

import com.example.common.request.ProductRequest;
import com.example.common.response.CommonResponse;
import com.example.common.response.ProductResponse;

import java.util.List;

/**
 * @author Tran Minh Truyen
 */
public interface ProductServices {
	boolean createProduct(ProductRequest productRequest);
	CommonResponse getAllProduct(int page, int size);
	CommonResponse getProductByKeyWord(int page, int size, String keyword);
	boolean updateProduct(int id, ProductRequest productRequest);
	boolean deleteProduct(int id);
}
