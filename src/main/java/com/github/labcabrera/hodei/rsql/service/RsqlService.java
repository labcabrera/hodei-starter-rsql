package com.github.labcabrera.hodei.rsql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;

public class RsqlService {

	@Autowired
	private RsqlCriteriaBuilder criteriaBuilder;

	public <E> Page<E> find(String rsql, Pageable pageable, Authentication auth, MongoTemplate mongoTemplate, Class<E> clazz) {
		Criteria criteria = criteriaBuilder.build(rsql, auth, clazz);
		Query query = new Query(criteria);
		long total = mongoTemplate.count(new Query(criteria), clazz);
		List<E> list = mongoTemplate.find(query.with(pageable), clazz);
		return new PageImpl<>(list, pageable, total);
	}

}
