package com.greenimpact.indicators.indicator;

import com.greenimpact.indicators.measures.MeasureDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorDTO {
    private String id;

    private Long organizationId;

    private MeasureDTO measure;

    private String name;

    private String type;

    private String framework;

    private List<Long> impact;

}
