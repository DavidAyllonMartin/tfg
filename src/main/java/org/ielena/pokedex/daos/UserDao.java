package org.ielena.pokedex.daos;

import org.ielena.pokedex.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
