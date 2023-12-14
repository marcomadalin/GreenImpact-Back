package com.greenimpact.plans.plan;

import com.greenimpact.plans.area.AreaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Plans")
public class PlanEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long organizationId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @OneToMany(mappedBy = "plan")
    private List<AreaEntity> areas;


    public PlanEntity(Long organizationId, String name, String description, LocalDate startDate, LocalDate endDate) {
        this.organizationId = organizationId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.areas = Collections.emptyList();
    }

    public PlanEntity(PlanDTO planDTO) {
        this.organizationId = planDTO.getOrganizationId();
        this.name = planDTO.getName();
        this.description = planDTO.getDescription();
        this.startDate = planDTO.getStartDate();
        this.endDate = planDTO.getEndDate();
        this.areas = Collections.emptyList();
    }

    public PlanDTO toDTO() {
        return new PlanDTO(id, organizationId, name, description, startDate, endDate,
                areas.stream().map(AreaEntity::toDTO).collect(Collectors.toList()));
    }
}
