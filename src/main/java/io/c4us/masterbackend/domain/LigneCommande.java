package io.c4us.masterbackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName; // Nom du produit commandé
    private int quantity;        // Quantité
    private double unitPrice;     // Prix unitaire
    private String codeStructure;


    @ManyToOne(fetch = FetchType.LAZY) // Plusieurs lignes de commande peuvent appartenir à une seule commande
    @JoinColumn(name = "order_id", nullable = false)
    private Command command; // Référence à la commande parente

    // Calcul du sous-total
    public double getSubTotal() {
        return quantity * unitPrice;
    }
}
