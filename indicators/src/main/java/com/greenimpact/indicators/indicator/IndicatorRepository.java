package com.greenimpact.indicators.indicator;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IndicatorRepository extends MongoRepository<IndicatorDocument, String> {

    List<IndicatorDocument> findByOrganizationId(Long organizationId);

    List<IndicatorDocument> findByOrganizationIdAndFramework(Long organizationId, Framework framework);

}
