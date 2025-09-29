package io.c4us.masterbackend.ressource;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.c4us.masterbackend.domain.Customer;
import io.c4us.masterbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerRessource {

    private final CustomerService customerService;

    

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            return ResponseEntity.created(URI.create("/customer/userID"))
                    .body(customerService.createCustomer(customer));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        return ResponseEntity.created(URI.create("/customer/update/userID"))
                .body(customerService.updateCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> getCustomer(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(customerService.getAllCustomer(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(customerService.getCustomer(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delCustomer(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(customerService.delCustomer(id));
    }

}
