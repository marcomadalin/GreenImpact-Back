package com.greenimpact.plans.sample;

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
@RequestMapping("/samples")
public class SampleController {

    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/{planId}/{areaId}/{areaIndicatorId}/all")
    public ResponseEntity<List<SampleDTO>> getAllSamples(@PathVariable Long planId, @PathVariable Long areaId,
                                                         @PathVariable Long areaIndicatorId) throws Exception {
        return ResponseEntity.ok().body(sampleService.getAllSamples(planId, areaId, areaIndicatorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SampleDTO> getSample(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(sampleService.getSample(id));
    }

    @PostMapping("/{planId}/{areaId}/{areaIndicatorId}/new")
    public ResponseEntity<SampleDTO> createSample(@PathVariable Long planId,
                                                  @PathVariable Long areaId,
                                                  @PathVariable Long areaIndicatorId,
                                                  @RequestBody SampleDTO sampleDTO) throws Exception {
        return ResponseEntity.ok().body(sampleService.createSample(planId, areaId, areaIndicatorId, sampleDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SampleDTO> updateSample(@PathVariable Long id, @RequestBody SampleDTO sampleDTO) throws Exception {
        return ResponseEntity.ok().body(sampleService.updateSample(id, sampleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SampleDTO> deleteSample(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(sampleService.deleteSample(id));
    }
}
