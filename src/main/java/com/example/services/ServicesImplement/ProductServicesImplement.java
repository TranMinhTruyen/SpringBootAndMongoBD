package com.example.services.ServicesImplement;

import com.example.common.entity.Brand;
import com.example.common.entity.Category;
import com.example.common.entity.Product;
import com.example.common.model.Cart;
import com.example.common.model.ListProduct;
import com.example.common.request.ProductRequest;
import com.example.common.response.CommonResponse;
import com.example.common.response.ProductResponse;
import com.example.repository.mongo.CartRepository;
import com.example.repository.mysql.BrandRepository;
import com.example.repository.mysql.CategoryRepository;
import com.example.repository.mysql.ProductRepository;
import com.example.repository.specification.ProductSpecification;
import com.example.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Tran Minh Truyen
 */

@Service
public class ProductServicesImplement implements ProductServices {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ProductResponse createProduct(ProductRequest productRequest){
		Optional<Brand> brand = brandRepository.findById(productRequest.getId_brand());
		Optional<Category> category = categoryRepository.findById(productRequest.getId_category());
		if (productRequest != null && !isExists(productRequest.getName()) && brand.isPresent() && category.isPresent()){
			Product newProduct = new Product();
			newProduct.setName(productRequest.getName());
			newProduct.setPrice(productRequest.getPrice());
			newProduct.setType(productRequest.getType());
			newProduct.setDiscount(productRequest.getDiscount());
			newProduct.setBrand(brand.get());
			newProduct.setCategory(category.get());
			newProduct.setImage(productRequest.getImage());
			newProduct.setUnitInStock(productRequest.getUnitInStock());
			Product result = productRepository.save(newProduct);
			if (result != null)
				return getProductAfterUpdateOrCreate(result);
			else return null;
		}
		else return null;
	}

	@Override
	public CommonResponse getAllProduct(int page, int size) {
		List<Product> productList = productRepository.findAll();
		List<ProductResponse> result = new ArrayList<>();
		productList.stream().forEach(items -> {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setId(items.getId());
			productResponse.setName(items.getName());
			productResponse.setPrice(items.getPrice());
			productResponse.setType(items.getType());
			productResponse.setBrand(items.getBrand().getName());
			productResponse.setCategory(items.getCategory().getName());
			productResponse.setDiscount(items.getDiscount());
			productResponse.setImage(items.getImage());
			result.add(productResponse);
		});
		if (!result.isEmpty()){
			return new CommonResponse().getCommonResponse(page, size, result);
		}
		else return null;
	}

	@Override
	public CommonResponse getProductByKeyWord(int page, int size, String name, String brand, String category, float price) {
		List<ProductResponse> productResponseList = filterProduct(name, brand, category, price);
		if (productResponseList != null){
			return new CommonResponse().getCommonResponse(page, size, productResponseList);
		}
		else return getAllProduct(page, size);
	}

	@Override
	public ProductResponse updateProduct(int id, ProductRequest productRequest) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()){
			Product update = product.get();
			update.setName(productRequest.getName());
			update.setType(productRequest.getType());
			update.setPrice(productRequest.getPrice());
			update.setUnitInStock(productRequest.getUnitInStock());
			update.setImage(productRequest.getImage());
			update.setDiscount(productRequest.getDiscount());
			Product result = productRepository.save(update);
			updateProductFromCart(id, update);
			if (result != null)
				return getProductAfterUpdateOrCreate(result);
			else return null;
		}
		return null;
	}

	@Override
	public boolean deleteProduct(int id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()){
			deleteProductFromCart(id);
			productRepository.deleteById(id);
			return true;
		}
		else return false;
	}

	public void updateProductFromCart(int productId, Product product){
		List<Cart> cartResult = cartRepository.findAll();
		float totalPrice = 0;
		for (Cart cart: cartResult){
			for (ListProduct listProduct: cart.getProductList()){
				if (listProduct.getId() == productId){
					listProduct.setProductName(product.getName());
					listProduct.setProductPrice(product.getPrice());
					listProduct.setDiscount(product.getDiscount());
					for (ListProduct items: cart.getProductList()){
						totalPrice += (items.getProductPrice()-(items.getProductPrice() * (items.getDiscount() / 100)))
								* items.getProductAmount();
					}
					cart.setTotalPrice(totalPrice);
					break;
				}
			}
		}
		cartRepository.saveAll(cartResult);
	}

	public void deleteProductFromCart(int productId){
		List<Cart> cartResult = cartRepository.findAll();
		float totalPrice = 0;

		for (Cart cart: cartResult){
			for (ListProduct listProduct: cart.getProductList()){
				if (listProduct.getId() == productId){
					cart.getProductList().remove(listProduct);
					for (ListProduct items: cart.getProductList()){
						totalPrice += (items.getProductPrice()-(items.getProductPrice() * (items.getDiscount() / 100)))
								* items.getProductAmount();
					}
					cart.setTotalPrice(totalPrice);
					break;
				}
			}
		}

		cartRepository.saveAll(cartResult);
		cartResult.stream().forEach(cart -> {
			if(cart.getProductList().isEmpty()){
				cartRepository.delete(cart);
			}
		});
	}

	@Override
	public boolean isExists(String productName) {
		return !productRepository.findAll(new ProductSpecification(productName)).isEmpty();
	}

	public ProductResponse getProductAfterUpdateOrCreate(Product product){
		ProductResponse response = new ProductResponse();
		response.setId(product.getId());
		response.setName(product.getName());
		response.setPrice(product.getPrice());
		response.setDiscount(product.getDiscount());
		response.setCategory(product.getCategory().getName());
		response.setBrand(product.getBrand().getName());
		response.setUnitInStock(product.getUnitInStock());
		response.setType(product.getType());
		response.setDiscount(product.getDiscount());
		response.setImage(product.getImage());
		return response;
	}

	private List<ProductResponse> filterProduct(@Nullable String name,
												@Nullable String brand,
												@Nullable String category,
												float price){
		List<Product> productList = productRepository.findAll();
		List<Product> filter = new ArrayList();
		List<ProductResponse> productResponseList = new ArrayList();
		if (name != null && price == 0){
			productList.stream().forEach(items -> {
				if (items.getName().toLowerCase().contains(name.toLowerCase())){
					filter.add(items);
				}
			});
		}

		if (category == null && brand != null && price == 0){
			productList.stream().forEach(items -> {
				if (items.getBrand().getName().toLowerCase().contains(brand.toLowerCase())){
					filter.add(items);
				}
			});
		}

		if (brand == null && category != null && price == 0){
			productList.stream().forEach(items -> {
				if (items.getCategory().getName().toLowerCase().contains(category.toLowerCase())){
					filter.add(items);
				}
			});
		}

		if (price != 0 && brand == null && category == null){
			productList.stream().forEach(items -> {
				if (items.getPrice() <= price){
					filter.add(items);
				}
			});
		}

		if (price != 0 && brand != null && category == null){
			productList.stream().forEach(items -> {
				if (items.getPrice() <= price && items.getBrand().getName().toLowerCase().contains(brand.toLowerCase())){
					filter.add(items);
				}
			});
		}

		if (price != 0 && brand == null && category != null){
			productList.stream().forEach(items -> {
				if (items.getPrice() <= price && items.getCategory().getName().toLowerCase().contains(category.toLowerCase())){
					filter.add(items);
				}
			});
		}

		if (price != 0 && brand != null && category != null){
			productList.stream().forEach(items -> {
				if (items.getPrice() <= price && items.getBrand().getName().toLowerCase().contains(brand.toLowerCase())
						&& items.getCategory().getName().toLowerCase().contains(category.toLowerCase())){
					filter.add(items);
				}
			});
		}

		if (brand != null && category != null && price == 0){
			productList.stream().forEach(items -> {
				if (items.getBrand().getName().toLowerCase().equals(brand.toLowerCase()) &&
						items.getCategory().getName().toLowerCase().equals(category.toLowerCase())){
					filter.add(items);
				}
			});
		}
		filter.stream().forEach(items -> {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setId(items.getId());
			productResponse.setName(items.getName());
			productResponse.setPrice(items.getPrice());
			productResponse.setType(items.getType());
			productResponse.setBrand(items.getBrand().getName());
			productResponse.setCategory(items.getCategory().getName());
			productResponse.setDiscount(items.getDiscount());
			productResponse.setImage(items.getImage());
			productResponseList.add(productResponse);
		});
		if (!productResponseList.isEmpty()){
			return productResponseList;
		}
		else
			return null;
	}
}
