package com.example.repository.specification;

import com.example.common.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public final class ProductSpecification implements Specification<Product> {

	private String keyword;

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		if (keyword != null){
			predicates.add(cb.like(cb.lower(root.get("name").as(String.class)), keyword.concat("%")));
			predicates.add(cb.like(cb.lower(root.get("id").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("price").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("type").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("category").get("name").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("brand").get("name").as(String.class)), keyword));
		}
		Predicate search = null;
		if (!predicates.isEmpty()){
			search = cb.or(predicates.toArray(new Predicate[] {}));
		}
		return search;
	}
}
