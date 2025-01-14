package com.anmol.inventory_service.dto;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Data @Getter @Setter @Builder
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
