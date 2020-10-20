package com.github.labcabrera.hodei.rsql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.labcabrera.hodei.rsql.parser.RsqlParser;

@Configuration
public class HodeiRsqlAutoConfiguration {

	@Bean
	RsqlParser rsqlParser() {
		return new RsqlParser();
	}

}
