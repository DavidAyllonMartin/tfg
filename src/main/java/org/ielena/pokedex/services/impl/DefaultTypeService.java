package org.ielena.pokedex.services.impl;

import jakarta.annotation.Resource;
import org.ielena.pokedex.daos.TypeDao;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.services.TypeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultTypeService implements TypeService {

    @Resource
    private TypeDao typeDao;

    @Override
    public List<TypeModel> findAll() {
        return typeDao.findAll();
    }

    @Override
    public Optional<TypeModel> findById(Long id) {
        return typeDao.findById(id);
    }

    @Override
    public void delete(Long id) {
        Optional.ofNullable(id)
                .ifPresent(typeDao::deleteById);
    }

    @Override
    public TypeModel save(TypeModel game) {
        return Optional.ofNullable(game)
                       .map(typeDao::save)
                       .orElseThrow();
    }

    @Override
    public void saveAll(Collection<TypeModel> types) {
        typeDao.saveAll(types);
    }
}
