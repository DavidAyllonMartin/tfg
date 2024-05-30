package org.ielena.pokedex.services;

import org.ielena.pokedex.models.TypeModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TypeService {

    List<TypeModel> findAll();

    Optional<TypeModel> findById(Long id);

    void delete(Long id);

    TypeModel save(TypeModel type);

    void saveAll(Collection<TypeModel> types);
}

