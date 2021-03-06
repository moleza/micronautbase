package com.mole.repository;

import java.util.List;

import com.mole.entity.Global;
import com.mole.records.GlobalIdTextRec;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface GlobalRepository extends CrudRepository<Global, Long> {
    List<GlobalIdTextRec> findAllByCodeAndEnabled(String code, Boolean enabled);
}