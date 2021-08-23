package com.core.response;

import com.core.model.ListProduct;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */
@Data
public class OrderResponse{
	private int id;
	private int customerId;
	private int employeeId;
	private Date createDate;
	private List<ListProduct> listProducts;
	private float totalPrice;
	private String status;
	private String andress;
}
