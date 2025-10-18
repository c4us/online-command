package io.c4us.masterbackend.domain;

import java.io.Serializable;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="structures")
public class Structure implements Serializable {
   @Id
    @UuidGenerator
    @Column(name = "id", unique = true, updatable = false)
    private String idStructure;
    private String nomStructure;
    private String phone1Structure;
    private String phone2Structure;
    private String paysStructure;
    private String villeStructure;
    private String rueStructure;
    private String codePoste;
    private String structPhotoUrl;
    private String emailStructure;
    private String typeStructure;
    private String disponibiliteStructure;
    private String geoLocStructure;
   // @Column(nullable = false)
    private boolean isActive =true;
    private LocalDateTime createdDate = LocalDateTime.now(); 
    private String descriptionStructure;
    
}
