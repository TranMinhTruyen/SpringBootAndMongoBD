package com.core.response;

import com.core.request.CategoryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryResponse extends CategoryRequest {
	private int id;
}
