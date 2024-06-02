package org.ielena.pokedex.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypeDto {

    private Long id;

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
