package com.greenimpact.plans.area;


import com.greenimpact.plans.areaIndicator.AreaIndicatorService;
import com.greenimpact.plans.plan.PlanEntity;
import com.greenimpact.plans.plan.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaService {

    private final AreaIndicatorService areaIndicatorService;

    private final PlanRepository planRepository;

    private final AreaRepository areaRepository;

    public AreaService(AreaIndicatorService areaIndicatorService, AreaRepository areaRepository, PlanRepository planRepository) {
        this.areaIndicatorService = areaIndicatorService;
        this.areaRepository = areaRepository;
        this.planRepository = planRepository;
    }

    public List<AreaDTO> getAllAreas(Long planId) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(planId);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");
        return areaRepository.findByPlan(planOpt.get()).stream().map(AreaEntity::toDTO).collect(Collectors.toList());
    }

    public AreaDTO getArea(Long id) throws Exception {
        Optional<AreaEntity> areaOpt = areaRepository.findById(id);
        if (areaOpt.isEmpty()) throw new Exception("AREA DOES NOT EXISTS");
        return areaOpt.get().toDTO();
    }

    public AreaDTO createArea(Long planId, AreaDTO areaDTO) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(planId);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");
        AreaEntity newArea = new AreaEntity(areaDTO);
        newArea.setPlan(planOpt.get());
        return areaRepository.save(newArea).toDTO();
    }

    public AreaDTO updateArea(Long id, AreaDTO areaDTO) throws Exception {
        Optional<AreaEntity> areaOpt = areaRepository.findById(id);
        if (areaOpt.isEmpty()) throw new Exception("AREA DOES NOT EXISTS");

        AreaEntity newArea = areaOpt.get();
        newArea.setName(areaDTO.getName());
        newArea.setDescription(areaDTO.getDescription());
        newArea.setStartDate(areaDTO.getStartDate());
        newArea.setEndDate(areaDTO.getEndDate());
        return areaRepository.save(newArea).toDTO();
    }

    public AreaDTO deleteArea(Long id) throws Exception {
        Optional<AreaEntity> areaOpt = areaRepository.findById(id);
        if (areaOpt.isEmpty()) throw new Exception("AREA DOES NOT EXISTS");
        areaOpt.get().getIndicators().forEach(areaIndicator -> {
            try {
                areaIndicatorService.deleteAreaIndicator(areaIndicator.getId());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        areaRepository.deleteById(id);
        return areaOpt.get().toDTO();
    }
}
