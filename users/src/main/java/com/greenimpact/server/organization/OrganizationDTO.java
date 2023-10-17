package com.greenimpact.server.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
@AllArgsConstructor
public class OrganizationDTO {
    public Long id;

    public String name;

    public List<Pair<String, String>> roles;
}
