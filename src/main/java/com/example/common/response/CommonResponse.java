package com.example.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Tran Minh Truyen
 */

@JsonPropertyOrder(value = {
		"data",
		"totalRecord",
		"page",
		"size",
		"totalPage"
})
@Data
public class CommonResponse implements Serializable {
	private Object[] data;
	private int totalRecord;
	private int page;
	private int size;
	private int totalPage;
}
