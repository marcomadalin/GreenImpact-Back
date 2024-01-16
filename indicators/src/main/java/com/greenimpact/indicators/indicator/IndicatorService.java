package com.greenimpact.indicators.indicator;

import com.greenimpact.indicators.measures.MeasureDTO;
import com.greenimpact.indicators.measures.MeasureDocument;
import com.greenimpact.indicators.measures.MeasureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;

    private final MeasureRepository measureRepository;


    public IndicatorService(IndicatorRepository indicatorRepository, MeasureRepository measureRepository) {
        this.indicatorRepository = indicatorRepository;
        this.measureRepository = measureRepository;
    }

    public List<IndicatorDTO> getAllIndicators(Long organizationId) {
        return indicatorRepository.findByOrganizationId(organizationId).stream().map(indicatorDocument -> {
            IndicatorDTO result = indicatorDocument.toDTO();
            Optional<MeasureDocument> measureOpt = measureRepository.findById(indicatorDocument.getMeasureId());
            if (measureOpt.isEmpty()) throw new RuntimeException("MEASURE DOES NOT EXIST");
            result.setMeasure(measureOpt.get().toDTO());
            return result;
        }).collect(Collectors.toList());
    }

    public List<IndicatorDTO> getAllFrameworkIndicators(Long organizationId, Framework framework) {
        return indicatorRepository.findByOrganizationIdAndFramework(organizationId, framework).stream().map(indicatorDocument -> {
            IndicatorDTO result = indicatorDocument.toDTO();
            Optional<MeasureDocument> measureOpt = measureRepository.findById(indicatorDocument.getMeasureId());
            if (measureOpt.isEmpty()) throw new RuntimeException("MEASURE DOES NOT EXIST");
            result.setMeasure(measureOpt.get().toDTO());
            return result;
        }).collect(Collectors.toList());
    }

    public List<MeasureDTO> getAllMeasures() {
        return measureRepository.findAll().stream().map(MeasureDocument::toDTO).collect(Collectors.toList());
    }

    public IndicatorDTO getIndicator(String id) throws Exception {
        Optional<IndicatorDocument> indicatorOpt = indicatorRepository.findById(id);
        if (indicatorOpt.isEmpty()) throw new Exception("INDICATOR DOES NOT EXIST");
        IndicatorDTO result = indicatorOpt.get().toDTO();

        Optional<MeasureDocument> measureOpt = measureRepository.findById(indicatorOpt.get().getMeasureId());
        if (measureOpt.isEmpty()) throw new Exception("MEASURE DOES NOT EXIST");
        result.setMeasure(measureOpt.get().toDTO());
        return result;
    }

    public IndicatorDTO createIndicator(IndicatorDTO indicatorDTO) {
        return indicatorRepository.save(new IndicatorDocument(indicatorDTO)).toDTO();
    }

    public IndicatorDTO updateIndicator(String id, IndicatorDTO indicatorDTO) throws Exception {
        Optional<IndicatorDocument> indicatorOpt = indicatorRepository.findById(id);
        if (indicatorOpt.isEmpty()) throw new Exception("INDICATOR DOES NOT EXIST");

        IndicatorDocument act = indicatorOpt.get();
        act.setName(indicatorDTO.getName());
        act.setType(IndicatorType.valueOf(indicatorDTO.getType()));
        act.setImpact(indicatorDTO.getImpact());

        return indicatorRepository.save(act).toDTO();
    }

    public void deleteIndicator(String id) {
        indicatorRepository.deleteById(id);
    }

}
