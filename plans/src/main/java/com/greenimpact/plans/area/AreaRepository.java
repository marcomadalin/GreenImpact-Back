package com.greenimpact.plans.area;

import com.greenimpact.plans.plan.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Long> {

    List<AreaEntity> findByPlan(PlanEntity plan);
}
