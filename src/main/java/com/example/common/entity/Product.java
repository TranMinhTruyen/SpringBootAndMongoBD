package com.example.common.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Tran Minh Truyen
 */

@Entity
@Data
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private Float price;

	@Column
	private String type;

	@ManyToOne()
	@JoinColumn(name = "ID_CATEGORY", nullable = true)
	private Category category;

	@ManyToOne()
	@JoinColumn(name = "ID_BRAND", nullable = true)
	private Brand brand;

	@Column
	private byte[] image;

	public int getId() {
		return id;
	}

	public void setCategory(int category) {
		Category id = new Category();
		id.setId(category);
		this.category = id;
	}

	public void setBrand(int brand) {
		Brand id = new Brand();
		id.setId(brand);
		this.brand = id;
	}
}
