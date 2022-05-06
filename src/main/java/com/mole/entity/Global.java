package com.mole.entity;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "global")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Global {
    @Id 
    @GeneratedValue 
    private Long id;

    @NotNull
    @NotBlank
    private String code;

    @NotNull
    @NotBlank
    private String text;

    @Nullable
    private String link;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;

    @DateCreated 
    @NotNull
    private Instant dateCreated;

    @DateUpdated 
    @NotNull
    private Instant dateUpdated;

}
