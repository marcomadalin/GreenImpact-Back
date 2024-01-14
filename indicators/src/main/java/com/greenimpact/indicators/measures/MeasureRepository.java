package com.greenimpact.indicators.measures;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasureRepository extends MongoRepository<MeasureDocument, String> {
}
