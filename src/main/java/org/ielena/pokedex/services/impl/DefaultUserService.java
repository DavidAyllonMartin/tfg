package org.ielena.pokedex.services.impl;

import jakarta.annotation.Resource;
import org.ielena.pokedex.daos.PokemonBasicInfoDao;
import org.ielena.pokedex.daos.UserDao;
import org.ielena.pokedex.models.PokemonBasicInfoModel;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.UserModel;
import org.ielena.pokedex.services.UserService;
import org.ielena.pokedex.singletons.UserSession;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private PokemonBasicInfoDao pokemonBasicInfoDao;
    @Resource
    private UserSession userSession;
    @Resource
    private Converter<PokemonBasicInfoModel, PokemonModel> pokemonModelConverter;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public UserModel findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Page<PokemonModel> findUserFavorites(Pageable pageable) {
        Long userId = userSession.getUserId();
        return userDao.findFavoritesByUserId(userId, pageable)
                      .map(pokemonModelConverter::convert);
    }

    @Override
    public boolean addFavoritePokemon(Long pokemonId) {
        Long userId = userSession.getUserId();
        System.out.println(pokemonId);
        Optional<PokemonBasicInfoModel> pokemonOptional = pokemonBasicInfoDao.findById(pokemonId);
        Optional<UserModel> userOptional = userDao.findById(userId);

        if (pokemonOptional.isPresent() && userOptional.isPresent()) {
            UserModel user = userOptional.get();
            PokemonBasicInfoModel pokemon = pokemonOptional.get();

            user.addFavorite(pokemon);
            userDao.saveAndFlush(user);
            return true;
        }

        return false;
    }

    public boolean removeFavoritePokemon(Long pokemonId) {
        Long userId = userSession.getUserId();
        System.out.println(pokemonId);
        Optional<PokemonBasicInfoModel> pokemonOptional = pokemonBasicInfoDao.findById(pokemonId);
        Optional<UserModel> userOptional = userDao.findById(userId);

        if (pokemonOptional.isPresent() && userOptional.isPresent()) {
            PokemonBasicInfoModel pokemon = pokemonOptional.get();
            UserModel user = userOptional.get();

            user.deleteFavorite(pokemon);
            userDao.saveAndFlush(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean isFavoritePokemon(Long pokemonId) {
        Long userId = userSession.getUserId();

        return userDao.isFavorite(userId, pokemonId);
    }
}

