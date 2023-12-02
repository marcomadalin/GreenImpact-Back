package com.greenimpact.plans.goal;


import com.greenimpact.plans.area.AreaEntity;
import com.greenimpact.plans.area.AreaRepository;
import com.greenimpact.plans.areaIndicator.AreaIndicatorEntity;
import com.greenimpact.plans.areaIndicator.AreaIndicatorRepository;
import com.greenimpact.plans.plan.PlanEntity;
import com.greenimpact.plans.plan.PlanRepository;
import com.greenimpact.plans.sample.SampleDTO;
import com.greenimpact.plans.sample.SampleEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoalService {

    private final PlanRepository planRepository;

    private final AreaRepository areaRepository;

    private final AreaIndicatorRepository areaIndicatorRepository;

    private final GoalRepository goalRepository;

    public GoalService(PlanRepository planRepository, AreaRepository areaRepository,
                       AreaIndicatorRepository areaIndicatorRepository, GoalRepository goalRepository) {
        this.planRepository = planRepository;
        this.areaRepository = areaRepository;
        this.areaIndicatorRepository = areaIndicatorRepository;
        this.goalRepository = goalRepository;
    }

    public List<SampleDTO> getAllGoals(Long planId, Long areaId, Long areaIndicatorId) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(planId);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");

        Optional<AreaEntity> areaOpt = areaRepository.findById(areaId);
        if (areaOpt.isEmpty()) throw new Exception("AREA DOES NOT EXISTS");

        Optional<AreaIndicatorEntity> areaIndicatorOpt = areaIndicatorRepository.findById(areaIndicatorId);
        if (areaIndicatorOpt.isEmpty()) throw new Exception("AREA INDICATOR DOES NOT EXISTS");

        return goalRepository.findByAreaIndicator(areaIndicatorOpt.get()).stream().map(SampleEntity::toDTO).collect(Collectors.toList());

    }

    public SampleDTO getGoal(Long id) throws Exception {
        Optional<GoalEntity> goalOpt = goalRepository.findById(id);
        if (goalOpt.isEmpty()) throw new Exception("GOAL DOES NOT EXISTS");
        return goalOpt.get().toDTO();
    }

    public SampleDTO createGoal(Long planId, Long areaId, Long areaIndicatorId, SampleDTO sampleDTO) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(planId);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");

        Optional<AreaEntity> areaOpt = areaRepository.findById(areaId);
        if (areaOpt.isEmpty()) throw new Exception("AREA DOES NOT EXISTS");

        Optional<AreaIndicatorEntity> areaIndicatorOpt = areaIndicatorRepository.findById(areaIndicatorId);
        if (areaIndicatorOpt.isEmpty()) throw new Exception("AREA INDICATOR DOES NOT EXISTS");

        GoalEntity newGoal = new GoalEntity(sampleDTO);
        newGoal.setAreaIndicator(areaIndicatorOpt.get());
        return goalRepository.save(newGoal).toDTO();
    }

    public SampleDTO updateGoal(Long id, SampleDTO sampleDTO) throws Exception {
        Optional<GoalEntity> goalOpt = goalRepository.findById(id);
        if (goalOpt.isEmpty()) throw new Exception("GOAL DOES NOT EXISTS");

        GoalEntity newGoal = goalOpt.get();
        newGoal.setValue(sampleDTO.getValue());
        newGoal.setNumeralValue(sampleDTO.getNumeralValue());
        newGoal.setTime(sampleDTO.getTime());
        return goalRepository.save(newGoal).toDTO();
    }

    public SampleDTO deleteGoal(Long id) throws Exception {
        Optional<GoalEntity> goalOpt = goalRepository.findById(id);
        if (goalOpt.isEmpty()) throw new Exception("GOAL DOES NOT EXISTS");
        goalRepository.deleteById(id);
        return goalOpt.get().toDTO();
    }
}
