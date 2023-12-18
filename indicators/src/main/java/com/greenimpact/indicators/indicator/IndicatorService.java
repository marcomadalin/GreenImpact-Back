package com.greenimpact.indicators.indicator;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;


    public IndicatorService(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    public List<IndicatorDTO> getAllIndicators() {
        return indicatorRepository.findAll().stream().map(IndicatorDocument::toDTO).collect(Collectors.toList());
    }

    public IndicatorDTO getIndicator(String id) throws Exception {
        Optional<IndicatorDocument> indicatorOpt = indicatorRepository.findById(id);
        if (indicatorOpt.isEmpty()) throw new Exception("INDICATOR DOES NOT EXIST");

        return indicatorOpt.get().toDTO();
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
