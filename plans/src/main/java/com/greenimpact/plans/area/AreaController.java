package com.greenimpact.plans.area;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping("/{planId}/all")
    public ResponseEntity<List<AreaDTO>> getAreas(@PathVariable Long planId) throws Exception {
        return ResponseEntity.ok().body(areaService.getAllAreas(planId));
    }

    @GetMapping("/{organizationId}/{indicatorId}/relatedAreas")
    public ResponseEntity<List<AreaDTO>> getRelatedIndicatorAreas(@PathVariable Long organizationId,
                                                                  @PathVariable String indicatorId) throws Exception {
        return ResponseEntity.ok().body(areaService.getRelatedIndicatorAreas(organizationId, indicatorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDTO> getArea(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(areaService.getArea(id));
    }

    @PostMapping("/{planId}/new")
    public ResponseEntity<AreaDTO> createArea(@PathVariable Long planId, @RequestBody AreaDTO areaDTO) throws Exception {
        return ResponseEntity.ok().body(areaService.createArea(planId, areaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaDTO> updateArea(@PathVariable Long id, @RequestBody AreaDTO areaDTO) throws Exception {
        return ResponseEntity.ok().body(areaService.updateArea(id, areaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AreaDTO> deleteArea(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(areaService.deleteArea(id));
    }
}
