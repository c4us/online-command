package io.c4us.masterbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.c4us.masterbackend.DTOs.CommandDto;
import io.c4us.masterbackend.domain.Command;
import io.c4us.masterbackend.domain.CommandLine;

import io.c4us.masterbackend.exception.ResourceNotFoundException;
import io.c4us.masterbackend.repo.CommandRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

@Service
@Slf4j
@jakarta.transaction.Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class CommandService {

    @Autowired
    private  CommandRepo commandRepo ;

 /*  public OrderService(CommandRepo commandRepo) {
        this.commandRepo = commandRepo;
    }*/  

    @Transactional // S'assure que toute l'opération est une transaction unique
    public Command createCommand(CommandDto commandDto) {
        Command command = new Command();
        command.setCustomerName(commandDto.getCustomerName());

        double totalAmount = 0;
        for (var itemLigneCommand : commandDto.getItems()) {
            CommandLine commandLine  = new CommandLine();
            commandLine.setProductName(itemLigneCommand.getProductName());
            commandLine.setQuantity(itemLigneCommand.getQuantity());
            commandLine.setUnitPrice(itemLigneCommand.getUnitPrice());

            command.addLigneCommande(commandLine); // Ajoute l'item et établit la relation bidirectionnelle
            totalAmount += commandLine.getSubTotal();
        }

        command.setTotalAmount(totalAmount);
        return commandRepo.save(command);
    }

    public List<Command> findAllCommand() {
        return commandRepo.findAll();
    }

    public Command findCommandById(Long id) {
        // Lance une exception spécifique si la ressource n'est pas trouvée
        return commandRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Command not found with id " + id));
    }

    @Transactional
    public Command updateStatus(Long id, String newStatus) {
        Command order = findCommandById(id); // Réutilise la méthode qui vérifie l'existence
        order.setStatus(newStatus);
        return commandRepo.save(order);
    }

    public void deleteOrder(Long id) {
        if (!commandRepo.existsById(id)) {
            throw new ResourceNotFoundException("Command not found with id " + id);
        }
        commandRepo.deleteById(id);
    }
}