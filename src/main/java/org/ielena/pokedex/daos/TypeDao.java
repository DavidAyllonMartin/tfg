package org.ielena.pokedex.daos;

import org.ielena.pokedex.models.TypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeDao extends JpaRepository<TypeModel, Long> {
}
