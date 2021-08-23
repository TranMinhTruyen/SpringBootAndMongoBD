package com.core.response;

import com.core.request.CartRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Tran Minh Truyen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CartResponse extends CartRequest {

	private float totalPrice;
}
