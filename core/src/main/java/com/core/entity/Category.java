package com.core.entity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */
@Entity
@Data
public class Category implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="category")
	private List<Product> productList;
}
