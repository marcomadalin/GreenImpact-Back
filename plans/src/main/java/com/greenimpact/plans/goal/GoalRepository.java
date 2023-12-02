package com.greenimpact.plans.goal;

import com.greenimpact.plans.areaIndicator.AreaIndicatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, Long> {
    List<GoalEntity> findByAreaIndicator(AreaIndicatorEntity areaIndicatorEntity);
}
