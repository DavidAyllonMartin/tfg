package org.ielena.pokedex.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ielena.pokedex.models.MoveModel;

public interface MoveDao extends JpaRepository<MoveModel, Long> {

}
