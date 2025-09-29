package io.c4us.masterbackend.DTOs;

import lombok.Data;

@Data
public class CommandLineDTO {
    private String productName;
    private int quantity;
    private double unitPrice;

}
