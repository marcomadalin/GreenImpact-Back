package com.greenimpact.indicators.indicator;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IndicatorRepository extends MongoRepository<IndicatorDocument, String> {
}
