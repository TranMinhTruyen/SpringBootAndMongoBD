package com.example.services.ServicesImplement;

import com.example.common.entity.Category;
import com.example.common.request.CategoryRequest;
import com.example.common.response.CategoryResponse;
import com.example.common.response.CommonResponse;
import com.example.repository.mysql.CategoryRepository;
import com.example.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServicesImp implements CategoryServices {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public boolean createCategory(CategoryRequest categoryRequest) {
		if (categoryRequest != null){
			Category newCategory = new Category();
			newCategory.setName(categoryRequest.getName());
			newCategory.setDescription(categoryRequest.getDescription());
			categoryRepository.save(newCategory);
		}
		if (categoryRepository.getByNameEquals(categoryRequest.getName()) && categoryRequest != null){
			return true;
		}
		return false;
	}

	@Override
	public CommonResponse getAllCategory(int page, int size) {
		return null;
	}

	@Override
	public CommonResponse getCategoryByKeyword(int page, int size, String keyword) {
		return null;
	}

	@Override
	public CategoryResponse updateCategory(int id, CategoryRequest categoryRequest) {
		return null;
	}

	@Override
	public boolean deleteCategory(int id) {
		return false;
	}
}
