package com.github.labcabrera.hodei.rsql.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import com.github.labcabrera.hodei.rsql.parser.RsqlParser;

public class RsqlCriteriaBuilder {

	private static final String AUTHORIZATION = "authorization";

	@Autowired
	private RsqlParser rsqlParser;

	public Criteria build(String rsql, Class<?> clazz) {
		Criteria criteria = rsqlParser.apply(rsql, clazz);
		return criteria != null ? criteria : new Criteria();
	}

	public Criteria build(String rsql, Authentication auth, Class<?> clazz) {
		Criteria unsecuredCriteria = rsqlParser.apply(rsql, clazz);
		List<String> authorities = readAuthorities(auth);
		return unsecuredCriteria != null ? unsecuredCriteria.and(AUTHORIZATION).in(authorities)
			: Criteria.where(AUTHORIZATION).in(authorities);
	}

	private List<String> readAuthorities(Authentication auth) {
		if (auth == null || auth.getAuthorities() == null) {
			throw new AccessDeniedException("Required authentication");
		}
		return auth.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList());
	}

}
