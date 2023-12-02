package com.greenimpact.plans.areaIndicator;


import com.greenimpact.plans.area.AreaEntity;
import com.greenimpact.plans.area.AreaRepository;
import com.greenimpact.plans.goal.GoalService;
import com.greenimpact.plans.plan.PlanEntity;
import com.greenimpact.plans.plan.PlanRepository;
import com.greenimpact.plans.sample.SampleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaIndicatorService {

    private final SampleService sampleService;

    private final GoalService goalService;

    private final PlanRepository planRepository;

    private final AreaRepository areaRepository;

    private final AreaIndicatorRepository areaIndicatorRepository;


    public AreaIndicatorService(SampleService sampleService, GoalService goalService, PlanRepository planRepository, AreaRepository areaRepository,
                                AreaIndicatorRepository areaIndicatorRepository) {
        this.sampleService = sampleService;
        this.goalService = goalService;
        this.planRepository = planRepository;
        this.areaRepository = areaRepository;
        this.areaIndicatorRepository = areaIndicatorRepository;
    }

    public List<AreaIndicatorDTO> getAllAreaIndicators(Long planId, Long areaId) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(planId);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");

        Optional<AreaEntity> areaOpt = areaRepository.findById(areaId);
        if (areaOpt.isEmpty()) throw new Exception("AREA DOES NOT EXISTS");

        return areaIndicatorRepository.findByArea(areaOpt.get()).stream().map(AreaIndicatorEntity::toDTO).collect(Collectors.toList());
    }

    public AreaIndicatorDTO getAreaIndicator(Long id) throws Exception {
        Optional<AreaIndicatorEntity> areaIndicatorOpt = areaIndicatorRepository.findById(id);
        if (areaIndicatorOpt.isEmpty()) throw new Exception("AREA INDICATOR DOES NOT EXISTS");
        return areaIndicatorOpt.get().toDTO();
    }

    public AreaIndicatorDTO createAreaIndicator(Long planId, Long areaId, AreaIndicatorDTO areaIndicatorDTO) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(planId);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");

        Optional<AreaEntity> areaOpt = areaRepository.findById(areaId);
        if (areaOpt.isEmpty()) throw new Exception("AREA DOES NOT EXISTS");

        AreaIndicatorEntity newArea = new AreaIndicatorEntity(areaIndicatorDTO);
        newArea.setArea(areaOpt.get());
        return areaIndicatorRepository.save(newArea).toDTO();
    }

    public AreaIndicatorDTO deleteAreaIndicator(Long id) throws Exception {
        Optional<AreaIndicatorEntity> areaIndicatorOpt = areaIndicatorRepository.findById(id);
        if (areaIndicatorOpt.isEmpty()) throw new Exception("AREA INDICATOR DOES NOT EXISTS");
        areaIndicatorOpt.get().getSamples().forEach(sample -> {
            try {
                sampleService.deleteSample(sample.getId());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        areaIndicatorOpt.get().getGoals().forEach(goal -> {
            try {
                goalService.deleteGoal(goal.getId());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        areaIndicatorRepository.deleteById(id);
        return areaIndicatorOpt.get().toDTO();
    }
}
