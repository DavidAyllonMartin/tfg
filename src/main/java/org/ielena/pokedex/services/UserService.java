package org.ielena.pokedex.services;

import org.ielena.pokedex.models.UserModel;

public interface UserService {

    void register(UserModel user);

    UserModel findByUsername(String username);
}
