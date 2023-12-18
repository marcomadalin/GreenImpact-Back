package com.greenimpact.indicators.indicator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorDTO {
    private String id;

    private String name;

    private String type;

    private String framework;

    private List<Long> impact;

}
