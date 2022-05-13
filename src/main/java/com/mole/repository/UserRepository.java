package com.mole.repository;

import java.util.Optional;

import com.mole.entity.User;
import com.mole.records.UserRec;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailAndPasswordAndEnabled(String email, String password, Boolean enabled);
    Optional<User> findByEmailAndEnabledAndLevelIsNotNull(String email, Boolean enabled);
    UserRec findByEmailAndEnabled(String email, Boolean enabled);
    Page<User> findAll(Pageable pageable);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}