package com.greenimpact.plans.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SampleDTO {

    private Long id;

    private LocalDateTime time;

    private String value;

    private Double numeralValue;

}
