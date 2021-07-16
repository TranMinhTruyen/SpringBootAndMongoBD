package com.example.common.request;

import com.example.common.model.Cart;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author Tran Minh Truyen
 */
@Data
public class OrderRequest {

	private int customerId;
	private int employeeId;
	private Date createDate;
	private Cart cart;
	private String status;
}
