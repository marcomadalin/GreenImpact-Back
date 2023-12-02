package com.greenimpact.plans.areaIndicator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
