package com.greenimpact.plans.areaIndicator;

import com.greenimpact.plans.area.AreaEntity;
import com.greenimpact.plans.goal.GoalEntity;
import com.greenimpact.plans.sample.SampleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
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

    @Column
    private TendencyEnum tendency;

    @OneToMany(mappedBy = "areaIndicator")
    private List<SampleEntity> samples;

    @OneToMany(mappedBy = "areaIndicator")
    private List<GoalEntity> goals;

    @ManyToOne
    private AreaEntity area;

    public AreaIndicatorEntity(Long indicatorId, AreaEntity area, TendencyEnum tendency) {
        this.indicatorId = indicatorId;
        this.area = area;
        this.tendency = tendency;
        this.goals = Collections.emptyList();
        this.samples = Collections.emptyList();
    }

    public AreaIndicatorEntity(AreaIndicatorDTO areaIndicatorDTO) {
        this.indicatorId = areaIndicatorDTO.getIndicatorId();
        this.tendency = TendencyEnum.valueOf(areaIndicatorDTO.getTendency());
        this.goals = Collections.emptyList();
        this.samples = Collections.emptyList();
    }

    public AreaIndicatorDTO toDTO() {
        return new AreaIndicatorDTO(id, indicatorId, tendency.toString(), samples.stream().map(SampleEntity::toDTO).collect(Collectors.toList()),
                goals.stream().map(SampleEntity::toDTO).collect(Collectors.toList()));
    }
}
