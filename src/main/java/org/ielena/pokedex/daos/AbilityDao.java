package org.ielena.pokedex.daos;

import org.ielena.pokedex.models.AbilityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbilityDao extends JpaRepository<AbilityModel, Long> {

}
