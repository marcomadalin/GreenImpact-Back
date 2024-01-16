package com.greenimpact.indicators.indicator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Indicators")
public class IndicatorDocument {
    @Id
    private String id;

    private String measureId;

    private Long organizationId;

    private String name;

    private IndicatorType type;

    private Framework framework;

    private List<Long> impact;

    public IndicatorDocument(String name, Long organizationId, String measureId, IndicatorType type, Framework framework, List<Long> impact) {
        this.organizationId = organizationId;
        this.measureId = measureId;
        this.name = name;
        this.type = type;
        this.framework = framework;
        this.impact = impact;
    }

    public IndicatorDocument(IndicatorDTO indicatorDTO) {
        this.organizationId = indicatorDTO.getOrganizationId();
        this.measureId = indicatorDTO.getMeasure().getId();
        this.name = indicatorDTO.getName();
        this.type = IndicatorType.valueOf(indicatorDTO.getType());
        this.framework = Framework.valueOf(indicatorDTO.getType());
        this.impact = indicatorDTO.getImpact();
    }

    public IndicatorDTO toDTO() {
        return new IndicatorDTO(id, organizationId, null, name, type.toString(), framework.toString(), impact);
    }
}
