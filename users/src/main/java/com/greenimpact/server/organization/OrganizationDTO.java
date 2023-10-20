package com.greenimpact.server.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
@AllArgsConstructor
public class OrganizationDTO {
    private Long id;

    private String name;

    private List<Pair<String, String>> roles;
}
