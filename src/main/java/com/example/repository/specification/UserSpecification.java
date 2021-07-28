package com.example.repository.specification;

import com.example.common.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {
	private String keyword;

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		if (keyword != null){
			predicates.add(cb.like(cb.lower(root.get("id").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("account").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("firstName").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("lastName").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("address").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("citizenId").as(String.class)), keyword));
			predicates.add(cb.like(cb.lower(root.get("role").as(String.class)), keyword));
		}
		Predicate search = null;
		if (!predicates.isEmpty()){
			search = cb.or(predicates.toArray(new Predicate[] {}));
		}
		return search;
	}
}
