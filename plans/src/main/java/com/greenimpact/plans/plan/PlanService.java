package com.greenimpact.plans.plan;


import com.greenimpact.plans.area.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanService {

    private final AreaService areaService;

    private final PlanRepository planRepository;

    public PlanService(AreaService areaService, PlanRepository planRepository) {
        this.areaService = areaService;
        this.planRepository = planRepository;
    }

    public List<PlanDTO> getAllPlans() {
        return planRepository.findAll().stream().map(PlanEntity::toDTO).collect(Collectors.toList());
    }

    public PlanDTO getPlan(Long id) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(id);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");
        return planOpt.get().toDTO();
    }

    public PlanDTO createPlan(PlanDTO planDTO) {
        return planRepository.save(new PlanEntity(planDTO)).toDTO();
    }

    public PlanDTO updatePlan(Long id, PlanDTO planDTO) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(id);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");

        PlanEntity newPlan = planOpt.get();
        newPlan.setName(planDTO.getName());
        newPlan.setDescription(planDTO.getDescription());
        newPlan.setStartDate(planDTO.getStartDate());
        newPlan.setEndDate(planDTO.getEndDate());
        return planRepository.save(newPlan).toDTO();
    }

    public PlanDTO deletePlan(Long id) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(id);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");
        planOpt.get().getAreas().forEach(area -> {
            try {
                areaService.deleteArea(area.getId());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        planRepository.deleteById(id);
        return planOpt.get().toDTO();
    }
}
