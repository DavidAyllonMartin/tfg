package org.ielena.pokedex.services;

public interface UserSessionService {

    void setUserId(Long userId);

    Long getUserId();

    void clear();
}