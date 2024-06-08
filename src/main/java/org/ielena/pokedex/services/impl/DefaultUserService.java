package org.ielena.pokedex.services.impl;

import jakarta.annotation.Resource;
import org.ielena.pokedex.daos.UserDao;
import org.ielena.pokedex.models.UserModel;
import org.ielena.pokedex.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    public void register(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    public UserModel findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}

