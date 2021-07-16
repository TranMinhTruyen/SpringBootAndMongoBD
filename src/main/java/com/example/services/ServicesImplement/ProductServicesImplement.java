package com.example.services.ServicesImplement;

import com.example.common.entity.Product;
import com.example.common.model.User;
import com.example.common.request.ProductRequest;
import com.example.common.response.CommonResponse;
import com.example.repository.mysql.ProductRepository;
import com.example.repository.specification.BrandSpecification;
import com.example.repository.specification.ProductSpecification;
import com.example.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

/**
 * @author Tran Minh Truyen
 */

@Service
public class ProductServicesImplement implements ProductServices {

	@Autowired
	private ProductRepository productRepository;

	@Override
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

	@Override
	public CommonResponse getAllProduct(int page, int size) {
		CommonResponse commonResponse = new CommonResponse();
		List result = productRepository.findAll();
		if (result != null){
			int offset = (page - 1) * size;
			int total = result.size();
			int totalPage = (total%size) == 0 ? (int)(total/size) : (int)((total / size) + 1);
			Object[] data = result.stream().skip(offset).limit(size).toArray();
			commonResponse.setData(data);
			commonResponse.setTotalPage(totalPage);
			commonResponse.setTotalRecord(total);
			commonResponse.setPage(page);
			commonResponse.setSize(size);
			return commonResponse;
		}
		else return null;
	}

	@Override
	public CommonResponse getProductByKeyWord(int page, int size, String keyword) {
		CommonResponse commonResponse = new CommonResponse();
		ProductSpecification specification = new ProductSpecification(keyword);
		List result = productRepository.findAll(specification);
		if (result != null){
			int offset = (page - 1) * size;
			int total = result.size();
			int totalPage = (total%size) == 0 ? (int)(total/size) : (int)((total / size) + 1);
			Object[] data = result.stream().skip(offset).limit(size).toArray();
			commonResponse.setData(data);
			commonResponse.setTotalPage(totalPage);
			commonResponse.setTotalRecord(total);
			commonResponse.setPage(page);
			commonResponse.setSize(size);
			return commonResponse;
		}
		else return null;
	}

	@Override
	public boolean updateProduct(int id, ProductRequest productRequest) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()){
			Product update = product.get();
			update.setName(productRequest.getName());
			update.setType(productRequest.getType());
			update.setPrice(productRequest.getPrice());
			update.setImage(productRequest.getImage());
			productRepository.save(update);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteProduct(int id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()){
			productRepository.deleteById(id);
			return true;
		}
		else return false;
	}
}
