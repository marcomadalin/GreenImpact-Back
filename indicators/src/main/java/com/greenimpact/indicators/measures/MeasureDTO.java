package com.greenimpact.indicators.measures;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasureDTO {
    private String id;

    private String name;

    private String type;

    private List<String> values;
}
