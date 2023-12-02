package com.greenimpact.plans.area;

import com.greenimpact.plans.areaIndicator.AreaIndicatorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaDTO {

    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<AreaIndicatorDTO> indicators;
}
