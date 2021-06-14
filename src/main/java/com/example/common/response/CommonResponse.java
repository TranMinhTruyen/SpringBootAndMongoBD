package com.example.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
public class CommonResponse implements Serializable {

	private Object[] data;
	private int totalRecord;
	private int page;
	private int size;
	private int totalPage;

	public CommonResponse() {

	}

	public CommonResponse(Object[] data, int totalRecord, int page, int size, int totalPage) {
		this.data = data;
		this.totalRecord = totalRecord;
		this.page = page;
		this.size = size;
		this.totalPage = totalPage;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
