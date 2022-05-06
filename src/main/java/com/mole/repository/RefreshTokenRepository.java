package com.mole.repository;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mole.entity.RefreshTokenEntity;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> { 

    @Transactional
    RefreshTokenEntity save(@NonNull @NotBlank String username,
                            @NonNull @NotBlank String refreshToken,
                            @NonNull @NotNull Boolean revoked); 

    Optional<RefreshTokenEntity> findByRefreshToken(@NonNull @NotBlank String refreshToken); 

    long updateByUsername(@NonNull @NotBlank String username,
                          @NonNull @NotNull Boolean revoked); 
}