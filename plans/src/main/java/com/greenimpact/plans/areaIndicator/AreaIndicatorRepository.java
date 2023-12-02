package com.greenimpact.plans.areaIndicator;

import com.greenimpact.plans.area.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaIndicatorRepository extends JpaRepository<AreaIndicatorEntity, Long> {
    List<AreaIndicatorEntity> findByArea(AreaEntity areaEntity);
}
