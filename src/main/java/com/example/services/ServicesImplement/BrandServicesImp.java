package com.example.services.ServicesImplement;

import com.example.common.entity.Brand;
import com.example.common.entity.Product;
import com.example.common.request.BrandRequest;
import com.example.common.response.BrandResponse;
import com.example.common.response.CommonResponse;
import com.example.repository.mysql.BrandRepository;
import com.example.repository.specification.BrandSpecification;
import com.example.services.BrandServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServicesImp implements BrandServices {

	@Autowired
	private BrandRepository brandRepository;

	@Override
	public boolean createBrand(BrandRequest brandRequest) {
		if (brandRequest != null){
			Brand newBrand = new Brand();
			newBrand.setName(brandRequest.getName());
			newBrand.setDescription(brandRequest.getDescription());
			brandRepository.save(newBrand);
			return true;
		}
		return false;
	}

	@Override
	public CommonResponse getAllBrand(int page, int size) {
		CommonResponse commonResponse = new CommonResponse();
		List<Brand> result = brandRepository.findAll();
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
		return null;
	}

	@Override
	public CommonResponse getBrandbyKeyword(int page, int size, String keyword) {
		CommonResponse commonResponse = new CommonResponse();
		BrandSpecification specification = new BrandSpecification(keyword);
		List<Brand> result = brandRepository.findAll(specification);
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
		return getAllBrand(page, size);
	}

	@Override
	public BrandResponse updateBrand(int id, BrandRequest brandRequest) {
		if (update(id, brandRequest)){
			Optional<Brand> brand = brandRepository.findById(id);
			Brand result = brand.get();
			BrandResponse brandResponse = new BrandResponse();
			brandResponse.setId(result.getId());
			brandResponse.setName(result.getName());
			brandResponse.setDescription(result.getDescription());
			return brandResponse;
		}
		return null;
	}

	@Override
	public boolean deleteBrand(int id) {
		Optional<Brand> brand = brandRepository.findById(id);
		if (brand.isPresent()){
			brandRepository.deleteById(id);
			return true;
		}
		return false;
	}

	private boolean update (int id, BrandRequest brandRequest){
		Optional<Brand> brand = brandRepository.findById(id);
		if (brandRequest != null && brand.isPresent()){
			Brand update = brand.get();
			update.setName(brandRequest.getName());
			update.setDescription(brandRequest.getDescription());
			brandRepository.save(update);
			return true;
		}
		return false;
	}
}
