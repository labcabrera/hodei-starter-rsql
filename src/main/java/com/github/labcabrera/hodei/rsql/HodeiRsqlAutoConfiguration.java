package com.github.labcabrera.hodei.rsql;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.labcabrera.hodei.rsql.parser.RsqlParser;
import com.github.labcabrera.hodei.rsql.service.RsqlCriteriaBuilder;

@Configuration
public class HodeiRsqlAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(RsqlParser.class)
	RsqlParser rsqlParser() {
		return new RsqlParser();
	}

	@Bean
	@ConditionalOnMissingBean(RsqlCriteriaBuilder.class)
	RsqlCriteriaBuilder rsqlCriteriaBuilder() {
		return new RsqlCriteriaBuilder();
	}

}
