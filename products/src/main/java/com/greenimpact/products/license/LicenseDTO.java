package com.greenimpact.products.license;

import com.greenimpact.products.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LicenseDTO {
    private Long id;

    private Long organizationId;

    private String key;

    private Boolean active;

    private Boolean enabled;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<ProductDTO> products;
}
