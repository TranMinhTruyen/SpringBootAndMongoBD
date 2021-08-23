package com.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */
@Entity
@Data
public class Brand implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private String description;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy="brand")
	private List<Product> productList;
}
