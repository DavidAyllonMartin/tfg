package org.ielena.pokedex.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface DatabaseUpdateService {

    Long updateDatabase(Integer limit, Integer offset) throws JsonProcessingException;
}
