package com.greenimpact.plans.sample;

import com.greenimpact.plans.areaIndicator.AreaIndicatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, Long> {

    List<SampleEntity> findByAreaIndicator(AreaIndicatorEntity areaIndicatorEntity);
}
