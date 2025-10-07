package io.c4us.masterbackend.domain;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name="products")
public class Product implements Serializable{
    @Id
    @UuidGenerator
    @Column(name = "id",unique = true,updatable = false)
    private String id;
    private String productId;
    private String productPhotoUrl;
    private String productName;
    private Date  createdDate;
    private String categoryId;
    @Transient 
    private Category categoryDetails; 
    private String codeStructure;
    private boolean isFavoris;

}