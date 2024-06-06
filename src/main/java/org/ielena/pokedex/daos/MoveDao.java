package org.ielena.pokedex.daos;

import org.ielena.pokedex.models.MoveModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveDao extends JpaRepository<MoveModel, Long> {
}
