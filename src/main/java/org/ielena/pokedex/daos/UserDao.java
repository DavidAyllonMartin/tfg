package org.ielena.pokedex.daos;

import org.ielena.pokedex.models.PokemonBasicInfoModel;
import org.ielena.pokedex.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);

    @Query("SELECT p FROM UserModel u JOIN u.favorites p WHERE u.id = :userId")
    Page<PokemonBasicInfoModel> findFavoritesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM UserModel u JOIN u.favorites p " +
            "WHERE u.id = :userId AND p.id = :pokemonId")
    boolean isFavorite(@Param("userId") Long userId, @Param("pokemonId") Long pokemonId);
}
