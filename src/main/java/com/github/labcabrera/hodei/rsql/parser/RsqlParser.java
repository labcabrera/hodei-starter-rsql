package com.github.labcabrera.hodei.rsql.parser;

import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import com.github.labcabrera.hodei.rsql.converter.CustomSpringConversionServiceConverter;
import com.github.labcabrera.hodei.rsql.exception.PredicateParseException;
import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.DefaultArgumentConversionPipe;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;

public class RsqlParser implements BiFunction<String, Class<?>, Criteria> {

	protected final QueryConversionPipeline pipeline;
	protected final MongoVisitor mongoVisitor;

	public RsqlParser() {
		DefaultArgumentConversionPipe pipe = DefaultArgumentConversionPipe.builder()
			.useNonDefaultStringToTypeConverter(new CustomSpringConversionServiceConverter())
			.build();
		pipeline = QueryConversionPipeline.builder()
			.useNonDefaultArgumentConversionPipe(pipe)
			.build();
		mongoVisitor = new CaseInsensitiveMongoVisitor();
	}

	public Criteria apply(String searchExpression, Class<?> entityClass) {
		try {
			if (StringUtils.isNoneBlank(searchExpression)) {
				Condition<GeneralQueryBuilder> condition = pipeline.apply(searchExpression, entityClass);
				return condition.query(mongoVisitor);
			}
			return null;
		}
		catch (RuntimeException ex) {
			throw new PredicateParseException(searchExpression, ex);
		}
	}

}
