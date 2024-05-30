package org.ielena.pokedex.services;

import org.ielena.pokedex.models.MoveModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MoveService {

    List<MoveModel> findAll();

    Optional<MoveModel> findById(Long id);

    void delete(Long id);

    MoveModel save(MoveModel move);

    void saveAll(Collection<MoveModel> moves);
}
