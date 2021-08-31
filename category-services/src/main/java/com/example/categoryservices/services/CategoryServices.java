package com.example.categoryservices.services;

import com.core.request.BrandRequest;
import com.core.request.CategoryRequest;
import com.core.response.BrandResponse;
import com.core.response.CategoryResponse;
import com.core.response.CommonResponse;

public interface CategoryServices {
    boolean createCategory(CategoryRequest categoryRequest);
    CommonResponse getAllCategory(int page, int size);
    CommonResponse getCategoryByKeyword(int page, int size, String keyword);
    CategoryResponse updateCategory(int id, CategoryRequest categoryRequest);
    boolean deleteCategory(int id);
    boolean isExists(String categoryName);
}
