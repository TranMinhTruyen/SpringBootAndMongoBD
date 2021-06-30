package com.example.common.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Tran Minh Truyen
 */

@Entity
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

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(int category) {
		Category id = new Category();
		id.setId(category);
		this.category = id;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(int brand) {
		Brand id = new Brand();
		id.setId(brand);
		this.brand = id;
	}
}
