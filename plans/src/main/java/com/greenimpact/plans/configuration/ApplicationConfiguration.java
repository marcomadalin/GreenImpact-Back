package com.greenimpact.plans.configuration;

import com.greenimpact.plans.area.AreaEntity;
import com.greenimpact.plans.area.AreaRepository;
import com.greenimpact.plans.areaIndicator.AreaIndicatorEntity;
import com.greenimpact.plans.areaIndicator.AreaIndicatorRepository;
import com.greenimpact.plans.areaIndicator.TendencyEnum;
import com.greenimpact.plans.goal.GoalEntity;
import com.greenimpact.plans.plan.PlanEntity;
import com.greenimpact.plans.plan.PlanRepository;
import com.greenimpact.plans.sample.SampleEntity;
import com.greenimpact.plans.sample.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(SampleRepository sampleRepository, AreaIndicatorRepository areaIndicatorRepository,
                                        AreaRepository areaRepository, PlanRepository planRepository) {
        return args -> {
            PlanEntity plan1 = new PlanEntity(1L, "CO2 Emission Reduction Plan", "LOREM IPSUM DOLOR SIT AMET",
                    LocalDate.now().minusYears(3), LocalDate.now().plusYears(2));
            plan1 = planRepository.save(plan1);

            PlanEntity plan2 = new PlanEntity(1L, "Water pollution decrease", "LOREM IPSUM DOLOR SIT AMET",
                    LocalDate.now().minusYears(5), LocalDate.now().plusYears(3));
            plan2 = planRepository.save(plan2);

            AreaEntity area1 = new AreaEntity("Hydraulic press construction", "LOREM IPSUM DOLOR SIT AMET",
                    LocalDate.now().minusYears(3), LocalDate.now().plusYears(2), plan2);
            area1 = areaRepository.save(area1);

            AreaIndicatorEntity indicator1 = new AreaIndicatorEntity(1L, area1, TendencyEnum.NEUTRAL);
            indicator1 = areaIndicatorRepository.save(indicator1);

            GoalEntity objective1 = new GoalEntity(LocalDateTime.now().plusYears(2), Boolean.toString(true), null, indicator1);
            objective1 = sampleRepository.save(objective1);

            SampleEntity sample = new SampleEntity(LocalDateTime.now().plusMonths(2), Boolean.toString(false), null, indicator1);
            sample = sampleRepository.save(sample);

            SampleEntity sample2 = new SampleEntity(LocalDateTime.now().plusMonths(3), Boolean.toString(false), null, indicator1);
            sample2 = sampleRepository.save(sample2);


        };
    }
}
