package com.greenimpact.plans.areaIndicator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/indicators")
public class AreaIndicatorController {

    private final AreaIndicatorService areaIndicatorService;

    public AreaIndicatorController(AreaIndicatorService areaIndicatorService) {
        this.areaIndicatorService = areaIndicatorService;
    }

    @GetMapping("/{planId}/{areaId}/all")
    public ResponseEntity<List<AreaIndicatorDTO>> getAreaIndicators(@PathVariable Long planId,
                                                                    @PathVariable Long areaId) throws Exception {
        return ResponseEntity.ok().body(areaIndicatorService.getAllAreaIndicators(planId, areaId));
    }

    @GetMapping("/{organizationId}/byTendency")
    public ResponseEntity<List<AreaIndicatorDTO>> getAllPositiveIndicators(@PathVariable Long organizationId,
                                                                           @RequestParam TendencyEnum tendency) throws Exception {
        return ResponseEntity.ok().body(areaIndicatorService.getAllPositiveIndicators(organizationId, tendency));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaIndicatorDTO> getAreaIndicator(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(areaIndicatorService.getAreaIndicator(id));
    }

    @PostMapping("/{planId}/{areaId}/new")
    public ResponseEntity<AreaIndicatorDTO> createAreaIndicator(@PathVariable Long planId,
                                                                @PathVariable Long areaId,
                                                                @RequestBody AreaIndicatorDTO areaIndicatorDTO) throws Exception {
        return ResponseEntity.ok().body(areaIndicatorService.createAreaIndicator(planId, areaId, areaIndicatorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AreaIndicatorDTO> deleteAreaIndicator(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(areaIndicatorService.deleteAreaIndicator(id));
    }
}
