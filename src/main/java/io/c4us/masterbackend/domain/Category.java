package io.c4us.masterbackend.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category implements Serializable {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, updatable = false)
    private String id;
    private String categoryId;
    private String nameCat;
    private String description;
    private LocalDateTime createdDate = LocalDateTime.now();
    private String codeStructure;
    private boolean isActive = true;
    @Transient
    private long productCount;

}
