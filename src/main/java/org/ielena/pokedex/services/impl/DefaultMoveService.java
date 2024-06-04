package org.ielena.pokedex.services.impl;

import jakarta.annotation.Resource;
import org.ielena.pokedex.daos.MoveDao;
import org.ielena.pokedex.models.MoveModel;
import org.ielena.pokedex.services.MoveService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultMoveService implements MoveService {

    @Resource
    private MoveDao moveDao;

    @Override
    public List<MoveModel> findAll() {
        return moveDao.findAll();
    }

    @Override
    public Optional<MoveModel> findById(Long id) {
        return moveDao.findById(id);
    }

    @Override
    public void delete(Long id) {
        Optional.ofNullable(id)
                .ifPresent(moveDao::deleteById);
    }

    @Override
    public MoveModel save(MoveModel game) {
        return Optional.ofNullable(game)
                       .map(moveDao::save)
                       .orElseThrow();
    }

    @Override
    public void saveAll(Collection<MoveModel> moves) {
        moveDao.saveAll(moves);
    }
}
