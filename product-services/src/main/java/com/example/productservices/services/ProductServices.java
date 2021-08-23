package com.example.productservices.services;

import com.core.request.ProductRequest;
import com.core.response.CommonResponse;
import com.core.response.ProductResponse;
import org.jetbrains.annotations.Nullable;

public interface ProductServices {
    ProductResponse createProduct(ProductRequest productRequest);
    CommonResponse getAllProduct(int page, int size);
    CommonResponse getProductByKeyWord(int page, int size,
                                       @Nullable String name,
                                       @Nullable String brand,
                                       @Nullable String category,
                                       float price);
    ProductResponse updateProduct(int id, ProductRequest productRequest);
    boolean deleteProduct(int id);
    boolean isExists(String productName);
}
