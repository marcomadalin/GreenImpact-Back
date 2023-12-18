package com.greenimpact.indicators.configuration;

import com.greenimpact.indicators.indicator.Framework;
import com.greenimpact.indicators.indicator.IndicatorDocument;
import com.greenimpact.indicators.indicator.IndicatorRepository;
import com.greenimpact.indicators.indicator.IndicatorType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(IndicatorRepository indicatorRepository) {
        return args -> {
            indicatorRepository.save(new IndicatorDocument("CO2 emission reduction", IndicatorType.QUANTITATIVE, Framework.MA2030, List.of(1L, 3L, 6L, 12L)));
        };
    }
}
