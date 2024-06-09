package org.ielena.pokedex.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ielena.pokedex.controllers.DatabaseUpdateListener;

public interface DatabaseUpdateService {

    Long updateDatabase(Integer limit, Integer offset) throws JsonProcessingException;

    void registerListener(DatabaseUpdateListener listener);

    void unregisterListener(DatabaseUpdateListener listener);
}