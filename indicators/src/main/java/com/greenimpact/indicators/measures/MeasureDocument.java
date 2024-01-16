package com.greenimpact.indicators.measures;

import com.greenimpact.indicators.indicator.IndicatorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Measures")
public class MeasureDocument {
    @Id
    private String id;

    private String name;

    private IndicatorType type;

    private List<String> values;

    public MeasureDocument(String name, IndicatorType type, List<String> values) {
        this.name = name;
        this.type = type;
        this.values = values;
    }

    public MeasureDocument(MeasureDTO measureDTO) {
        this.name = measureDTO.getName();
        this.type = IndicatorType.valueOf(measureDTO.getType());
        this.values = measureDTO.getValues();
    }

    public MeasureDTO toDTO() {
        return new MeasureDTO(id, name, type.toString(), values);
    }
}
