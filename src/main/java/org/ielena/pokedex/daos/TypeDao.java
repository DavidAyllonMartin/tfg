package org.ielena.pokedex.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ielena.pokedex.models.TypeModel;

public interface TypeDao extends JpaRepository<TypeModel, Long> {

}
