package com.core.response;

import com.core.request.BrandRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BrandResponse extends BrandRequest {
	private int id;
}
