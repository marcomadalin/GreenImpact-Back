package com.greenimpact.plans.areaIndicator;

import com.greenimpact.plans.area.AreaEntity;
import com.greenimpact.plans.goal.GoalEntity;
import com.greenimpact.plans.sample.SampleEntity;
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

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "AreaIndicators")
public class AreaIndicatorEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long indicatorId;

    @OneToMany(mappedBy = "areaIndicator")
    private List<SampleEntity> samples;

    @OneToMany(mappedBy = "areaIndicator")
    private List<GoalEntity> goals;

    @ManyToOne
    private AreaEntity area;

    public AreaIndicatorEntity(Long indicatorId, AreaEntity area) {
        this.indicatorId = indicatorId;
        this.area = area;
    }

    public AreaIndicatorEntity(AreaIndicatorDTO areaIndicatorDTO) {
        this.indicatorId = areaIndicatorDTO.getIndicatorId();
    }

    public AreaIndicatorDTO toDTO() {
        return new AreaIndicatorDTO(id, indicatorId, samples.stream().map(SampleEntity::toDTO).collect(Collectors.toList()),
                goals.stream().map(SampleEntity::toDTO).collect(Collectors.toList()));
    }
}
