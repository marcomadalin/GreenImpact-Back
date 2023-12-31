package com.greenimpact.plans.plan;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/{organizationId}/all")
    public ResponseEntity<List<PlanDTO>> getPlans(@PathVariable Long organizationId) {
        return ResponseEntity.ok().body(planService.getAllPlans(organizationId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> getPlan(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(planService.getPlan(id));
    }

    @PostMapping("/new")
    public ResponseEntity<PlanDTO> createPlan(@RequestBody PlanDTO planDTO) {
        return ResponseEntity.ok().body(planService.createPlan(planDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable Long id, @RequestBody PlanDTO planDTO) throws Exception {
        return ResponseEntity.ok().body(planService.updatePlan(id, planDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlanDTO> deletePlan(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(planService.deletePlan(id));
    }
}
