package com.greenimpact.plans.sample;

import com.greenimpact.plans.areaIndicator.AreaIndicatorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Sample")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "sampleType", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
public class SampleEntity {
    @Id
    @GeneratedValue
    protected Long id;

    @Column
    protected LocalDateTime time;

    @Column
    protected String value;

    @Column
    protected Double numeralValue;

    @ManyToOne
    @ToString.Exclude
    protected AreaIndicatorEntity areaIndicator;

    public SampleEntity(LocalDateTime time, String value, Double numeralValue, AreaIndicatorEntity areaIndicator) {
        this.time = time;
        this.value = value;
        this.numeralValue = numeralValue;
        this.areaIndicator = areaIndicator;
    }

    public SampleEntity(SampleDTO sampleDTO) {
        this.time = sampleDTO.getTime();
        this.value = sampleDTO.getValue();
        this.numeralValue = sampleDTO.getNumeralValue();
    }

    public SampleDTO toDTO() {
        return new SampleDTO(id, time, value, numeralValue);
    }
}
