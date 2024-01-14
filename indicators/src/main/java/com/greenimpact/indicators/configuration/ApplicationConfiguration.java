package com.greenimpact.indicators.configuration;

import com.greenimpact.indicators.indicator.Framework;
import com.greenimpact.indicators.indicator.IndicatorDocument;
import com.greenimpact.indicators.indicator.IndicatorRepository;
import com.greenimpact.indicators.indicator.IndicatorType;
import com.greenimpact.indicators.measures.MeasureDocument;
import com.greenimpact.indicators.measures.MeasureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(IndicatorRepository indicatorRepository, MeasureRepository measureRepository) {
        return args -> {
            MeasureDocument measure = measureRepository.save(new MeasureDocument("Weight", List.of("Kg")));
            indicatorRepository.save(new IndicatorDocument("CO2 emission reduction", measure.getId(), IndicatorType.QUANTITATIVE, Framework.MA2030, List.of(1L, 3L, 6L, 12L)));
        };
    }
}
