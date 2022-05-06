package com.mole.repository;

import java.util.Optional;

import com.mole.entity.User;
import com.mole.entity.UserProfile;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserProfileRepository extends CrudRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {
    Optional<UserProfile> findByUser(User user);
}