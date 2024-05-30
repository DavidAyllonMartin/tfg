package org.ielena.pokedex.services.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.ielena.pokedex.daos.AbilityDao;
import org.ielena.pokedex.models.AbilityModel;
import org.ielena.pokedex.services.AbilityService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultAbilityService implements AbilityService {

    @Resource
    private AbilityDao abilityDao;

    @Override
    public List<AbilityModel> findAll() {
        return abilityDao.findAll();
    }

    @Override
    public Optional<AbilityModel> findById(Long id) {
        return abilityDao.findById(id);
    }

    @Override
    public void delete(Long id) {
        Optional.ofNullable(id)
                .ifPresent(abilityDao::deleteById);
    }

    @Override
    public AbilityModel save(AbilityModel game) {
        return Optional.ofNullable(game)
                       .map(abilityDao::save)
                       .orElseThrow();
    }

    @Override
    public void saveAll(Collection<AbilityModel> abilities) {
        abilityDao.saveAll(abilities);
    }
}
