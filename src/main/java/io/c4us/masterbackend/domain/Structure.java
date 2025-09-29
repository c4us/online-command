package io.c4us.masterbackend.domain;

import java.io.Serializable;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private String codeStructure;
    private String typeStructure;
    private String disponibiliteStructure;
    private String isActive;

}
