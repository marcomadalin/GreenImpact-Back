package com.greenimpact.plans.goal;

import com.greenimpact.plans.sample.SampleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/{planId}/{areaId}/{areaIndicatorId}/all")
    public ResponseEntity<List<SampleDTO>> getAllGoals(@PathVariable Long planId, @PathVariable Long areaId,
                                                       @PathVariable Long areaIndicatorId) throws Exception {
        return ResponseEntity.ok().body(goalService.getAllGoals(planId, areaId, areaIndicatorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SampleDTO> getGoal(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(goalService.getGoal(id));
    }

    @PostMapping("/{planId}/{areaId}/{areaIndicatorId}/new")
    public ResponseEntity<SampleDTO> createGoal(@PathVariable Long planId,
                                                @PathVariable Long areaId,
                                                @PathVariable Long areaIndicatorId,
                                                @RequestBody SampleDTO sampleDTO) throws Exception {
        return ResponseEntity.ok().body(goalService.createGoal(planId, areaId, areaIndicatorId, sampleDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SampleDTO> updateGoal(@PathVariable Long id, @RequestBody SampleDTO sampleDTO) throws Exception {
        return ResponseEntity.ok().body(goalService.updateGoal(id, sampleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SampleDTO> deleteGoal(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(goalService.deleteGoal(id));
    }
}
