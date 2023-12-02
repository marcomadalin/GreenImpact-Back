package com.greenimpact.plans.sample;


import com.greenimpact.plans.area.AreaEntity;
import com.greenimpact.plans.area.AreaRepository;
import com.greenimpact.plans.areaIndicator.AreaIndicatorEntity;
import com.greenimpact.plans.areaIndicator.AreaIndicatorRepository;
import com.greenimpact.plans.plan.PlanEntity;
import com.greenimpact.plans.plan.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SampleService {

    private final PlanRepository planRepository;

    private final AreaRepository areaRepository;

    private final AreaIndicatorRepository areaIndicatorRepository;

    private final SampleRepository sampleRepository;

    public SampleService(PlanRepository planRepository, AreaRepository areaRepository,
                         AreaIndicatorRepository areaIndicatorRepository, SampleRepository sampleRepository) {
        this.planRepository = planRepository;
        this.areaRepository = areaRepository;
        this.areaIndicatorRepository = areaIndicatorRepository;
        this.sampleRepository = sampleRepository;
    }

    public List<SampleDTO> getAllSamples(Long planId, Long areaId, Long areaIndicatorId) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(planId);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");

        Optional<AreaEntity> areaOpt = areaRepository.findById(areaId);
        if (areaOpt.isEmpty()) throw new Exception("AREA DOES NOT EXISTS");

        Optional<AreaIndicatorEntity> areaIndicatorOpt = areaIndicatorRepository.findById(areaIndicatorId);
        if (areaIndicatorOpt.isEmpty()) throw new Exception("AREA INDICATOR DOES NOT EXISTS");

        return sampleRepository.findByAreaIndicator(areaIndicatorOpt.get()).stream().map(SampleEntity::toDTO).collect(Collectors.toList());

    }

    public SampleDTO getSample(Long id) throws Exception {
        Optional<SampleEntity> sampleOpt = sampleRepository.findById(id);
        if (sampleOpt.isEmpty()) throw new Exception("SAMPLE DOES NOT EXISTS");
        return sampleOpt.get().toDTO();
    }

    public SampleDTO createSample(Long planId, Long areaId, Long areaIndicatorId, SampleDTO sampleDTO) throws Exception {
        Optional<PlanEntity> planOpt = planRepository.findById(planId);
        if (planOpt.isEmpty()) throw new Exception("PLAN DOES NOT EXISTS");

        Optional<AreaEntity> areaOpt = areaRepository.findById(areaId);
        if (areaOpt.isEmpty()) throw new Exception("AREA DOES NOT EXISTS");

        Optional<AreaIndicatorEntity> areaIndicatorOpt = areaIndicatorRepository.findById(areaIndicatorId);
        if (areaIndicatorOpt.isEmpty()) throw new Exception("AREA INDICATOR DOES NOT EXISTS");

        SampleEntity newSample = new SampleEntity(sampleDTO);
        newSample.setAreaIndicator(areaIndicatorOpt.get());
        return sampleRepository.save(newSample).toDTO();
    }

    public SampleDTO updateSample(Long id, SampleDTO sampleDTO) throws Exception {
        Optional<SampleEntity> sampleOpt = sampleRepository.findById(id);
        if (sampleOpt.isEmpty()) throw new Exception("SAMPLE DOES NOT EXISTS");

        SampleEntity newSample = sampleOpt.get();
        newSample.setValue(sampleDTO.getValue());
        newSample.setNumeralValue(sampleDTO.getNumeralValue());
        newSample.setTime(sampleDTO.getTime());
        return sampleRepository.save(newSample).toDTO();
    }

    public SampleDTO deleteSample(Long id) throws Exception {
        Optional<SampleEntity> sampleOpt = sampleRepository.findById(id);
        if (sampleOpt.isEmpty()) throw new Exception("SAMPLE DOES NOT EXISTS");
        sampleRepository.deleteById(id);
        return sampleOpt.get().toDTO();
    }
}
