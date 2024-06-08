package org.ielena.pokedex.daos;

import org.ielena.pokedex.models.PokemonBasicInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonBasicInfoDao extends JpaRepository<PokemonBasicInfoModel, Long> {
}
