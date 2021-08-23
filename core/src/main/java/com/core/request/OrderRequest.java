package com.core.request;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author Tran Minh Truyen
 */
@Data
public class OrderRequest {
	private int employeeId;
	private String andress;
	private String status;
}
