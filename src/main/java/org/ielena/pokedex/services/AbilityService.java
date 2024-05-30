package org.ielena.pokedex.services;

import org.ielena.pokedex.models.AbilityModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AbilityService {

    List<AbilityModel> findAll();

    Optional<AbilityModel> findById(Long id);

    void delete(Long id);

    AbilityModel save(AbilityModel ability);

    void saveAll(Collection<AbilityModel> abilities);
}
