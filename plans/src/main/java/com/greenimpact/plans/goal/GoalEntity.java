package com.greenimpact.plans.goal;

import com.greenimpact.plans.areaIndicator.AreaIndicatorEntity;
import com.greenimpact.plans.sample.SampleDTO;
import com.greenimpact.plans.sample.SampleEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Entity
@DiscriminatorValue("2")
public class GoalEntity extends SampleEntity {
    public GoalEntity(LocalDateTime time, String value, Double numeralValue, AreaIndicatorEntity areaIndicator) {
        super(time, value, numeralValue, areaIndicator);
    }

    public GoalEntity(SampleDTO sampleDTO) {
        super(sampleDTO);
    }
}
