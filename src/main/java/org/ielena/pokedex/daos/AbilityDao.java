package org.ielena.pokedex.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ielena.pokedex.models.AbilityModel;

public interface AbilityDao extends JpaRepository<AbilityModel, Long> {

}
