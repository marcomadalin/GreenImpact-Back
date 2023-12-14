package com.greenimpact.plans.area;

import com.greenimpact.plans.areaIndicator.AreaIndicatorEntity;
import com.greenimpact.plans.plan.PlanEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Areas")
public class AreaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @OneToMany(mappedBy = "area")
    private List<AreaIndicatorEntity> indicators;

    @ManyToOne
    private PlanEntity plan;

    public AreaEntity(String name, String description, LocalDate startDate, LocalDate endDate, PlanEntity plan) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plan = plan;
        this.indicators = Collections.emptyList();
    }

    public AreaEntity(AreaDTO areaDTO) {
        this.name = areaDTO.getName();
        this.description = areaDTO.getDescription();
        this.startDate = areaDTO.getStartDate();
        this.endDate = areaDTO.getEndDate();
        this.indicators = Collections.emptyList();
    }

    public AreaDTO toDTO() {
        return new AreaDTO(id, name, description, startDate, endDate,
                indicators.stream().map(AreaIndicatorEntity::toDTO).collect(Collectors.toList()));
    }
}
