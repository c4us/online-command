package io.c4us.masterbackend.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class AppUser {
    @Id
    @UuidGenerator
    @Column(name = "id",unique = true,updatable = false)
    private String id;
    private String userName;
    private String userEmail;
    private String userPhone;
    private LocalDateTime createdDate = LocalDateTime.now();
    private boolean isActive = false;
    private String userPassword;
    private String confirmationToken; 
    private LocalDateTime tokenExpiryDate;
}
