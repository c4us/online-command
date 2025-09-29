package io.c4us.masterbackend.DTOs;
import java.util.List;

import lombok.Data;

@Data
public class CommandDto {    
    private String customerName;
    private String customerId;
    private List<CommandLineDTO> items;

}
