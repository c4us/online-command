package io.c4us.masterbackend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity
@Data
public class Command {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String status = "PENDING";
    private LocalDateTime orderDate = LocalDateTime.now();
    private double totalAmount; // Calculé par le service
    private String codeStructure;


    // Une commande a plusieurs lignes de commande (items)
    // CascadeType.ALL: Si l'Order est supprimée, les OrderItems associés le sont aussi.
    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandLine> items = new ArrayList<>();

    // Méthode utilitaire pour synchroniser la relation bidirectionnelle
    public void addLigneCommande(CommandLine ligneCommande) {
        items.add(ligneCommande);
        ligneCommande.setCommand(this);

}
}
