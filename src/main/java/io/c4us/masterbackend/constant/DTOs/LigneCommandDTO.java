package io.c4us.masterbackend.constant.DTOs;

import lombok.Data;

@Data
public class LigneCommandDTO {
    private String productName;
    private int quantity;
    private double unitPrice;

}
