package com.mole.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 50, columnDefinition = "VARCHAR(50)")
    @NotBlank
    @NotNull
    private String name;
    @Column(length = 50, columnDefinition = "VARCHAR(50)")
    @NotBlank
    @NotNull
    private String surname;
    @Column(length = 50, columnDefinition = "VARCHAR(50)", unique = true)
    private String business;
    @Email
    @NotNull
    @Column(unique=true)
    private String email;
    @NotBlank
    @NotNull
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    @Column(length = 100, columnDefinition = "VARCHAR(100)")
    @NotBlank
    @NotNull
    // @JsonIgnore //Wont convert from POST to user password if this is ignored.
    private String password;
    @NotBlank
    @NotNull
    @Column(columnDefinition = "int default 0")
    private Integer level;
    @Column(length = 50, columnDefinition = "VARCHAR(255)")
    private String roles;
}
