package com.example.brandservices.services;

import com.core.request.BrandRequest;
import com.core.response.BrandResponse;
import com.core.response.CommonResponse;

public interface BrandServices {
    boolean createBrand(BrandRequest brandRequest);
    CommonResponse getAllBrand(int page, int size);
    CommonResponse getBrandbyKeyword(int page, int size, String keyword);
    BrandResponse updateBrand(int id, BrandRequest brandRequest);
    boolean deleteBrand(int id);
    boolean isExists(String brandName);
}
