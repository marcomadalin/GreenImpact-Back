package com.greenimpact.plans.plan;

import com.greenimpact.plans.area.AreaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDTO {

    private Long id;

    private Long organizationId;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<AreaDTO> areas;
}
