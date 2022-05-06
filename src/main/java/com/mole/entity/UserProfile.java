package com.mole.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull;
import io.micronaut.core.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfile {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @Column(unique=true)
    @NotNull
    private User user;

    @Column(length = 15, columnDefinition = "VARCHAR(15)", unique=true)
    @Nullable
    private String mobile;

    @Column(length = 7, columnDefinition = "VARCHAR(7)")
    @Nullable
    private String gender;
    
    @Nullable
    private LocalDate birthdate;
}
