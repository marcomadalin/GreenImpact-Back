package com.greenimpact.indicators.measures;

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

    private List<String> values;

    public MeasureDocument(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    public MeasureDocument(MeasureDTO measureDTO) {
        this.name = measureDTO.getName();
        this.values = measureDTO.getValues();
    }

    public MeasureDTO toDTO() {
        return new MeasureDTO(id, name, values);
    }
}
