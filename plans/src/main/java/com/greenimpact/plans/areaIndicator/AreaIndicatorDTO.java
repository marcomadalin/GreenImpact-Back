package com.greenimpact.plans.areaIndicator;

import com.greenimpact.plans.sample.SampleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaIndicatorDTO {

    private Long id;

    private Long indicatorId;

    private String tendency;

    private List<SampleDTO> samples;

    private List<SampleDTO> goals;

}
