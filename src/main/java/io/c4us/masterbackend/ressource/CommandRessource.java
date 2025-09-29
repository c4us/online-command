package io.c4us.masterbackend.ressource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.c4us.masterbackend.constant.DTOs.CommandDto;
import io.c4us.masterbackend.domain.Command;
import io.c4us.masterbackend.service.CommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/command")
@RequiredArgsConstructor
public class CommandRessource {
    
    private final CommandService commandService ;

   /*  public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }*/

    // POST /api/orders : Créer une nouvelle commande (utilise le DTO)
    @PostMapping
    // @Valid active la validation du DTO
    public ResponseEntity<Command> createCommand(@Valid @RequestBody CommandDto commandDtoDto) {
        Command savedCommand = commandService.createCommand(commandDtoDto);
        return new ResponseEntity<>(savedCommand, HttpStatus.CREATED);
    }

    // GET /api/orders : Récupérer toutes les commandes
    @GetMapping
    public List<Command> getAllCommand() {
        return commandService.findAllCommand();
    }

    // GET /api/orders/{id} : Récupérer une commande par ID (retourne 404 si non trouvée via l'exception)
    @GetMapping("/{id}")
    public Command getOrderById(@PathVariable Long id) {
        return commandService.findCommandById(id);
    }

    // PUT /api/orders/{id}/status : Mettre à jour le statut
    @PutMapping("/{id}/status")
    // Note: Utiliser String comme RequestBody pour une simple chaîne est acceptable ici
    public Command updateOrderStatus(@PathVariable Long id, @RequestBody String newStatus) {
        return commandService.updateStatus(id, newStatus);
    }

    // DELETE /api/orders/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        commandService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
