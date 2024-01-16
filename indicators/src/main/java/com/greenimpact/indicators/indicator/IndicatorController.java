package com.greenimpact.indicators.indicator;

import com.greenimpact.indicators.measures.MeasureDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/indicators")
public class IndicatorController {
    
    private final IndicatorService indicatorService;


    public IndicatorController(IndicatorService indicatorService) {
        this.indicatorService = indicatorService;
    }

    @GetMapping("{organizationId}/all")
    public ResponseEntity<List<IndicatorDTO>> getIndicators(@PathVariable Long organizationId) {
        return ResponseEntity.ok().body(indicatorService.getAllIndicators(organizationId));
    }

    @GetMapping("{organizationId}/byFramework")
    public ResponseEntity<List<IndicatorDTO>> getAllFrameworkIndicators(@PathVariable Long organizationId, @RequestParam Framework framework) {
        return ResponseEntity.ok().body(indicatorService.getAllFrameworkIndicators(organizationId, framework));
    }

    @GetMapping("/allMeasures")
    public ResponseEntity<List<MeasureDTO>> getMeasures() {
        return ResponseEntity.ok().body(indicatorService.getAllMeasures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndicatorDTO> getIndicator(@PathVariable String id) throws Exception {
        IndicatorDTO result = indicatorService.getIndicator(id);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/new")
    public ResponseEntity<IndicatorDTO> createIndicator(@RequestBody IndicatorDTO indicatorDTO) {
        return ResponseEntity.ok().body(indicatorService.createIndicator(indicatorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IndicatorDTO> updateIndicator(@PathVariable String id, @RequestBody IndicatorDTO indicatorDTO) throws Exception {
        IndicatorDTO result = indicatorService.updateIndicator(id, indicatorDTO);

        if (result == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIndicator(@PathVariable String id) {

        indicatorService.deleteIndicator(id);
        return ResponseEntity.ok()
                .body("Product deleted");
    }
}
